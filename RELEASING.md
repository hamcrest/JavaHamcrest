# Release Procedure

These instructions are a how-to guide for Hamcrest maintainers. They describe
how to release a new version of Hamcrest to Maven Central.

## Signing Key Setup

The Gradle `signing` plugin is used to cryptographically sign the published
artifacts. You will need to add details of your private keys to allow
the plugin to work. Follow the instructions at
https://docs.gradle.org/current/userguide/signing_plugin.html.

You can store the signing properties in a file called `gradle.properties`
as a sibling of the top level `build.gradle` file. This file will be ignored
by `git`, and so will not be accidentally checked in. Alternatively, you
can put the `gradle.properties` file in the `GRADLE_USER_HOME` directory
(usually `~/.gradle`)

## Sonatype Account Setup

Before you can perform a release, you will need:
* An account on the Maven Central staging server at https://oss.sonatype.org/
* Permissions in the `org.hamcrest` group

### Register an Account

You will need to register an account with https://central.sonatype.com/.
Follow the instructions at https://central.sonatype.org/register/legacy/#create-an-account
for actually creating an account. Do not follow the instructions to
"Create a Namespace". `org.hamcrest` is already managed through the legacy
project infrastructure. That may change in the future, but for now
(July 2024) these instructions work as described below.

### Request to be Added to `org.hamcrest`

Sonatype needs to be notified that you have access to publish to
`org.hamcrest`. This must be done by one of the current publishers
(e.g. `@tumbarumba` or `@sf105`). Ask one of those people to follow
the instructions at https://central.sonatype.org/register/legacy/#contact-central-support-at-sonatype.
This may take up to 2 days before your permissions are applied.

### Configure Access Tokens

https://oss.sonatype.org/ will no longer accept a username and password
when publishing. Rather, you will need to create an access token. Follow
the instructions at https://central.sonatype.org/publish/generate-token/,
and store the values in `ossrhUsername` and `ossrhPassword` in the same
`gradle.properties` file used to hold the signing keys.

In the end, you should have a `gradle.properties` file with (at least) the
following keys:

```properties
signing.keyId=...
signing.password=...
signing.secretKeyRingFile=...
ossrhUsername=...
ossrhPassword=...
```

## Publishing Process

### Update Version

The version is defined as a property at the top of the `build.gradle` file
in the root of the repository. Normally, this has a `-SNAPSHOT` suffix. When
doing a release, the `-SNAPSHOT` suffix is removed.

Edit the file to set the `version` property to the actual version required.
Save the file and test out the publishing process locally by running the
command:

```shell
./gradlew clean jar publishToMavenLocal
```

To check the local publish worked correctly, look in the directory
`~/.m2/repository/org/hamcrest/hamcrest` and verify the versioned files
are all present and correct.

When you are satisfied that the build is working correctly, you can commit
the change, tag the repository, and push to GitHub.

e.g. this is the commands for version 3.0:

```shell
git add build.gradle
git commit -m "Version 3.0"
git tag v3.0
git push origin --tags
```

### Publishing to Sonatype Staging 

When ready, run the command:

```shell
./gradlew publishAllPublicationsToMavenRepository
```

Wait.

Wait a bit more. Sometimes it times out. Sometimes you will get 502 errors.
Keep on trying. It will eventually work.


### Release to Maven Central

Look at the staging repositories: https://oss.sonatype.org/#stagingRepositories
and find the published artifacts. Make sure that everything is present
and accounted for.

When you are happy, you will need to "Close" the repository - you won't be
able to publish any more artifacts to this repository, but you will then
be able to release the repository.

Once it is closed, you will be able to "Release" the repository, which will
transfer all artifacts to Maven Central. It can take up to half an hour
for the jars to appear. Look in https://mvnrepository.com/artifact/org.hamcrest/hamcrest
and check for the new version.

### Prepare for Next Snapshot

Edit the `build.gradle` file, and change the version back to the next
snapshot version. For example, if you just published version `3.0`, the
next version will be `3.1-SNAPSHOT`.

Commit this change and push to GitHub.

### Share and Enjoy

Put a message out on the mailing lists to give people the good news.

# Future Improvements

Look at https://github.com/gradle-nexus/publish-plugin/ to automate all
this.
