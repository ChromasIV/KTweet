[![Gradle Build](https://github.com/ChromasIV/KTweet/actions/workflows/gradle-build.yml/badge.svg?branch=master)](https://github.com/ChromasIV/KTweet/actions/workflows/gradle-build.yml) [![Gradle Tests](https://github.com/ChromasIV/KTweet/actions/workflows/gradle-tests.yml/badge.svg)](https://github.com/ChromasIV/KTweet/actions/workflows/gradle-tests.yml)
# KTweet - A Kotlin Twitter Library
KTweet is a library that allows you to use the Twitter API v2.

Interested in Kotlin or KTweet? Join the [Discord](https://discord.gg/aSBXXkzb3f)

## Import
[![KTweet on Maven](https://img.shields.io/maven-central/v/com.chromasgaming/ktweet?label=latest%20version)](https://central.sonatype.com/search?namespace=com.chromasgaming&q=g%253Acom.chromasgaming%2520a%253Aktweet)

### Maven
```
<dependency>
  <groupId>com.chromasgaming</groupId>
  <artifactId>ktweet</artifactId>
  <version>1.3.0</version>
</dependency>
```

### Gradle Kotlin DSL

```
implementation("com.chromasgaming:ktweet:1.3.0")
```

## Setup
1. Obtain the API keys from your Twitter Developer portal. Follow this [guide](https://developer.twitter.com/en/docs/twitter-api/getting-started/getting-access-to-the-twitter-api).
2. Save them locally and add them into your environment variables. 
3. Run [ManageTweetsTest.kt](src/test/kotlin/com/chromasgaming/ktweet/api/ManageTweetsAPITest.kt) to validate no issues.

## Contribution  
All contribution to the library is welcomed.
Ensure any request follows the following:

 - [Contributing](https://github.com/ChromasIV/KTweet/blob/ChromasIV-contributing-draft-1/CONTRIBUTING.md)
 - [Feature Request](https://github.com/ChromasIV/KTweet/blob/master/.github/ISSUE_TEMPLATE/feature_request.md)
 - [Bug Report](https://github.com/ChromasIV/KTweet/blob/master/.github/ISSUE_TEMPLATE/bug_report.md)


## License
   Copyright 2021 Thomas P. Carney

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
   
   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
