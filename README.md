sb## Order Service with gRPC

Based on https://blog.rockthejvm.com/grpc-in-scala-with-fs2-scalapb/

This order service project is developed with Scalapb and fs2-grpc. This code is associated with [this](https://blog.rockthejvm.com/) article written by me on the rockthejvm.com blog.

http://localhost:8080/index.html

```shell
grpcurl localhost:9999 list
    # Failed to dial target host "localhost:9999": tls: first record does not look like a TLS handshake
    # Googled grpcurl "tls: first record does not look like a TLS handshake"

# Based on https://github.com/fullstorydev/grpcurl/issues/200
grpcurl -plaintext localhost:9999 list
    # Failed to list services: server does not support the reflection API
```

After adding ProtoReflectionService in OrderService.runServer, based on
https://github.com/grpc/grpc-java/blob/master/documentation/server-reflection-tutorial.md#enable-server-reflection
```shell
grpcurl -plaintext localhost:9999 list
```
    com.rockthejvm.protos.Order
    grpc.reflection.v1alpha.ServerReflection
