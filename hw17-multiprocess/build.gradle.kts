import com.google.protobuf.gradle.id

plugins {
    id("idea")
    id("com.google.protobuf")
}

val errorProneAnnotations = "2.28.0"
val tomcatAnnotationsApi = "6.0.53"
val grpc = "1.64.0"
val grpcProtobuf = "3.25.3"


dependencies {
    implementation("io.grpc:grpc-netty:$grpc")
    implementation("io.grpc:grpc-protobuf:$grpc")
    implementation("io.grpc:grpc-stub:$grpc")
    implementation("com.google.protobuf:protobuf-java:$grpcProtobuf")
    implementation("com.google.errorprone:error_prone_annotations:$errorProneAnnotations")
    implementation("org.apache.tomcat:annotations-api:$tomcatAnnotationsApi")
    implementation("ch.qos.logback:logback-classic")
}

val protoSrcDir = "$projectDir/build/generated/sources/proto"

idea {
    module {
        sourceDirs = sourceDirs.plus(file("$protoSrcDir/main/java"))
        sourceDirs = sourceDirs.plus(file("$protoSrcDir/main/grpc"))
    }
}

sourceSets {
    main {
        java {
            srcDirs(
                "$protoSrcDir/main/java",
                "$protoSrcDir/main/grpc"
            )
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$grpcProtobuf"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpc"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") {
                    outputSubDir = "grpc"
                }
            }
        }
    }
}

tasks.named("generateProto") {
    dependsOn(tasks.named("processResources"))
}

tasks.named("clean") {
    doLast {
        delete(protoSrcDir)
    }
}