# MOL-Multiplatform-Client

This is the repository for the multiplatform-client of the MOL-Organization.

## Installing

This project uses compose-kotlin-multiplatform and therefore compiles natively to the following targets:

- Android
- Desktop (JVM native)
- Web (WebAssemblyJS)

The app can be downloaded in these package formats:

- Desktop
  - Windows
    - Microsoft Installer (.msi)
    - Executable (.exe)
  - Linux
    - Debian archive (.deb)
- Android
  - Android Package (.apk)
  - Android App Bundle (.aab)
- Web
  - Static web distribution (single page)

Download the latest package of your choice from
the [Releases page](https://github.com/my-own-lawbook/mplat-client/releases)

## Contributing

### Making changes

If you want to push changes, do the following steps:

- (If needed: fork the repository)
- Choose or a create an issue/milestone in which you describe the problem/new feature
- Create a new branch, and name it:
  - If for milestone: milestone-<milestone-id>
  - If for issue: issue-<issue-id>
- Do your changes, prefixing the commits with the id of the issue the commit is related to, e.g.: 371: Did some changes
- State your changes in the ./changelogs/next-changelog.md (or create if it doesn't exist)
- Create a pull request

### Create a release

If you want to create a release, do the following steps:

- Merge the develop branch into the main or master branch
- Rename ./changelogs/next-changelog.md to <release-semver>.md and adjust content if needed
- Bump version fields
  - `./composeApp/build.gradle.kts#android`:
    - Increment `versionCode` by one
    - Set `versionName` to the semver
  - `./composeApp/build.gradle.kts#compose.desktop`:
    - Set `packageVersion` to the semver
- Trigger the 'release.yml' workflow and enter the semver as the release version
