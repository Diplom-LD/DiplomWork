import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("jacoco") // Обычный JaCoCo не работает для Android Unit Test без кастомной задачи
    id("com.github.nbaztec.coveralls-jacoco") version "1.2.20"
}

android {
    namespace = "com.example.diplomwork"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.diplomwork"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.6")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

// Настроим задачу для генерации отчета для Debug версии
tasks.register("jacocoTestReportDebug", JacocoReport::class) {
    dependsOn("testDebugUnitTest")  // Убедитесь, что тесты выполняются

    reports {
        xml.required.set(true)
        xml.outputLocation.set(file("$rootDir/jacoco-reports/testDebugUnitTest.xml")) // Путь к XML отчету

        html.required.set(true)
        html.outputLocation.set(file("$rootDir/jacoco-reports/testDebugUnitTest")) // Папка для HTML отчета
    }

    // Путь к исходным кодам
    sourceDirectories.setFrom(files("$projectDir/src/main/java"))
    classDirectories.setFrom(files("$buildDir/tmp/kotlin-classes/debug"))

    // Указываем, где брать данные о покрытии
    executionData.setFrom(fileTree(buildDir).include("jacoco/testDebugUnitTest.exec"))
}

// Настроим задачу для генерации отчета для Release версии
tasks.register("jacocoTestReportRelease", JacocoReport::class) {
    dependsOn("testReleaseUnitTest")  // Убедитесь, что тесты выполняются

    reports {
        xml.required.set(true)
        xml.outputLocation.set(file("$rootDir/jacoco-reports/testReleaseUnitTest.xml")) // Путь к XML отчету

        html.required.set(true)
        html.outputLocation.set(file("$rootDir/jacoco-reports/testReleaseUnitTest")) // Папка для HTML отчета
    }

    sourceDirectories.setFrom(files("$projectDir/src/main/java"))
    classDirectories.setFrom(files("$buildDir/tmp/kotlin-classes/release"))
    executionData.setFrom(fileTree(buildDir).include("jacoco/testReleaseUnitTest.exec"))
}

// Настройка проверки покрытия
tasks.register("jacocoTestCoverageVerification", JacocoCoverageVerification::class) {
    dependsOn("jacocoTestReportDebug", "jacocoTestReportRelease")  // Проверка для обеих версий

    violationRules {
        rule {
            element = "BUNDLE"
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.8".toBigDecimal()  // Минимальное покрытие 80%
            }
        }
    }
}

// Проверка покрытия на выполнение
tasks.check {
    dependsOn("jacocoTestCoverageVerification")
}
