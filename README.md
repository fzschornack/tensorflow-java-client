# tensorflow-java-client
Example of Java/Scala grpc client for tensorflow_serving (https://github.com/tensorflow/serving)

# How to generate the Java service files (Mac OS X 10.10.5)

## grpc-java

first you have to install `protobuf` (and `protoc`) in your machine (the current version is 3.1.0v):
```brew install protobuf```

then you have to build the `grpc-java plugin` (https://github.com/grpc/grpc-java/tree/master/compiler) 
and also the `grpc-java` project (https://github.com/grpc/grpc-java) to use the generated libs:

``` 
git clone https://github.com/grpc/grpc-java.git
cd compiler
../gradlew java_pluginExecutable
```

The generated plugin is inside `<project-root>/compiler/build/exe/java_plugin/`

To build the `grpc-java` project, first create the file <project-root>/gradle.properties and add the line skipCodegen=true into it.
Then, inside terminal, navigate to the root directory and execute

```
./gradlew_build
```

The generated libs are inside the folders

```<project-root>/<module>/build/libs/```

I had many dependency problems, that is why I had to build the `grpc-java` code and use the libs created during the build (the `grpc-java` version available in `mavencentral` seems to be outdated).

## tensorflow_serving

Then you have to compile the `tensorflow_serving .proto files` inside 
`serving/tensorflow_serving/apis` and `serving/tensorflow/tensorflow/core/framework`.

The `.proto` files inside `apis` have dependencies that are inside `..tensorflow/core/framework/` so, to make things easier, 
I copied the proto files from `apis` to `framework`.

I had problems with the import paths used inside the `.proto` files so I had to edit them, e.g.:
```import tensorflow/core/framework/some.proto 

was changed to

import some.proto```

because all the .proto files were in the same directory. 

Inside terminal, navigate to `serving/tensorflow/tensorflow/core/framework` and compile the files using the java-plugin with the command:
```protoc -I=$SRC_DIR --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java --grpc-java_out=$OUT_DIR --java_out=$OUT_DIR *.proto```

My `$SRC_DIR` is `.` (the folder I am already in) and my `$OUT_DIR` is `java-pb-files`:
```/usr/local/Cellar/protobuf/3.1.0/bin/protoc -I=. --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java \
--grpc-java_out=java-pb-files --java_out=java-pb-files *.proto```

