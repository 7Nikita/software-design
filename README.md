# BSUIR. FCSN. Term 5. Software design.

- [Tasks](#Tasks)
  - [Lab 1 - Hello world](#1-hello-world)
  - [Lab 2 - Numbers Mason, what do they mean?](#2-numbers-mason-what-do-they-mean)

## 1. Hello world

1. Create a plain Android App project. (target version is Android 6). All work must be performed inside git repository.
2. Set-up a basic app versioning. Application package should have proper version code and version name according to SEMVER (Semantic Versioning). Version name should contain major, minor and patch version digits. Version tracking should be performed by utilizing git tag feature. Each tag should be formatted as "MAJOR.MINOR" (e.g. 1.3), PATCH number represents a number of commits performed after latest release (latest tag).
3. Resulting **release** APK should be signed with personal certificate automatically during build.
4. The only activity of application should display current version name and device ID (which is secured by corresponding runtime permission).
5. You should properly handle runtime permission and give user a dummy explanation why it is necessary to grant this permission. App should withstand screen rotation and other possible test-cases.

## 2. Numbers Mason, what do they mean?

1. Create 'Calculator' app.
2. Application should have two modes: basic and scientific (see, well, any calculator for reference).
3. Modes can be toggled via interface button.
4. Each set of functions (numbers + basic functions and scientific functions) should be in separate fragments.
5. For landscape device orientation app should always be in scientific mode.
6. Create a 'demo' build flavor with only basic functions available for both screen orientations. User should be able to have both 'demo' and 'full' versions on device at the same time.
