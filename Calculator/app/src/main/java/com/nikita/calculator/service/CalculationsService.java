package com.nikita.calculator.service;

import androidx.annotation.NonNull;

import java.util.Set;

import com.udojava.evalex.Expression;
import com.google.common.collect.ImmutableSet;


public class CalculationsService {

    private static final CalculationsService sInstance = new CalculationsService();

    public static CalculationsService getInstance() {
        return sInstance;
    }

    private CalculationsService() {}

    private final Set<String> mFunctions = ImmutableSet.of(
            "sin(",
            "cos(",
            "tan(",
            "lg(",
            "ln(",
            "sqrt(",
            "fact(",
            "PI"
    );

    @NonNull
    private StringBuilder mExpression = new StringBuilder();

    private boolean wasError = false;

    public String evaluate(@NonNull String text) {
        update(text);
        return mExpression.toString();
    }

    private void update(String text) {

        if (wasError) {
            wasError = false;
            mExpression = new StringBuilder();
        }

        checkEmpty(text);

        switch (text) {
            case "!":
                mExpression.append("fact(");
                break;
            case "sin":
            case "cos":
            case "tan":
            case "ln":
            case "lg":
                mExpression.append(text).append("(");
                break;
            case "√":
                mExpression.append("sqrt(");
                break;
            case "⌫":
                onDelete();
                break;
            case "AC":
                onFullDelete();
                break;
            case "=":
                onEquals();
                break;
            default:
                mExpression.append(text);
        }
    }

    private void onDelete() {
        final int expressionLength = mExpression.length();
        if (expressionLength == 0)
            return;
        final String exp = mExpression.toString();
        for (String suffix : mFunctions) {
            if (exp.endsWith(suffix)) {
                mExpression = new StringBuilder(exp.substring(0, exp.length() - suffix.length()));
                return;
            }
        }
        mExpression.delete(expressionLength - 1, expressionLength);
    }

    private void onFullDelete() {
        mExpression = new StringBuilder();
    }

    private void checkEmpty(String text) {
        if (mExpression.length() == 0)
            return;

        boolean left = Character.isDigit(mExpression.charAt(mExpression.length() - 1)) &&
                Character.isAlphabetic(text.charAt(text.length() - 1));
        boolean onlyAlpha = Character.isAlphabetic(text.charAt(text.length() - 1)) &&
                Character.isAlphabetic(mExpression.charAt(mExpression.length() - 1));
        boolean right = Character.isDigit(text.charAt(text.length() - 1)) &&
                Character.isAlphabetic(mExpression.charAt(mExpression.length() - 1));
        if (left || right || onlyAlpha) {
            mExpression.append("*");
        }
    }

    private void refactor() {
        String exp = mExpression.toString();
        exp = exp.replaceAll("ln", "log");
        exp = exp.replaceAll("lg", "log10");
        mExpression = new StringBuilder(exp);
    }

    private void onEquals() {
        refactor();
        Expression exp = new Expression(mExpression.toString());
        try {
            mExpression = new StringBuilder(exp.eval().stripTrailingZeros().toPlainString());
        } catch (Exception e) {
            wasError = true;
            mExpression = new StringBuilder(e.getMessage());
        }
    }
}
