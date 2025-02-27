@file:Suppress("DEPRECATION") // App projects should still use FirebaseTestLabPlugin.

import com.google.firebase.gradle.plugins.ci.device.FirebaseTestLabExtension
import com.google.firebase.gradle.plugins.ci.device.FirebaseTestLabPlugin

/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("com.google.gms.google-services")
  id("com.google.firebase.crashlytics")
  id("com.google.firebase.firebase-perf")
}

android {
  namespace = "com.google.firebase.testing.sessions"
  compileSdk = 33
  defaultConfig {
    applicationId = "com.google.firebase.testing.sessions"
    minSdk = 16
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"
    multiDexEnabled = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions { jvmTarget = "1.8" }
}

dependencies {
  if (project.hasProperty("useReleasedVersions")) {
    val latestReleasedVersion: String by project
    println("Using sessions released version: $latestReleasedVersion")
    // TODO(mrober): How to find the released versions of crashlytics and perf?
    implementation("com.google.firebase:firebase-crashlytics:18.4.3")
    implementation("com.google.firebase:firebase-perf:20.4.1")
    implementation("com.google.firebase:firebase-sessions:$latestReleasedVersion")
  } else {
    implementation(project(":firebase-crashlytics"))
    implementation(project(":firebase-perf"))
    implementation(project(":firebase-sessions"))
  }

  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  implementation("androidx.multidex:multidex:2.0.1")
  implementation("com.google.android.material:material:1.9.0")
  implementation(libs.androidx.core)

  androidTestImplementation("com.google.firebase:firebase-common-ktx:20.3.3")
  androidTestImplementation(libs.androidx.test.junit)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.truth)
}

extra["packageName"] = "com.google.firebase.testing.sessions"

apply(from = "../../gradle/googleServices.gradle")

apply<FirebaseTestLabPlugin>()

configure<FirebaseTestLabExtension> {
  device("model=panther,version=33") // Pixel7
}