package com.rockthejvm

import cats.effect.*
import com.rockthejvm.protos.orders.*
import fs2.grpc.syntax.all.*
import io.grpc.*
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.*
import lab.hello.*

object Server {
  // Bind the defined service
  private val orderService: Resource[IO, ServerServiceDefinition] =
    OrderFs2Grpc.bindServiceResource[IO](new OrderService)

  private val helloService: Resource[IO, ServerServiceDefinition] =
    GreeterFs2Grpc.bindServiceResource[IO](new GreeterService[IO, Metadata])

  // Run the defined services
  private def runServer(orderServiceD: ServerServiceDefinition, helloServiceD: ServerServiceDefinition): Resource[IO, Server] = {
    val nsBuilder: NettyServerBuilder = NettyServerBuilder
      .forPort(9999)
      // Based on https://github.com/grpc/grpc-java/blob/master/documentation/server-reflection-tutorial.md#enable-server-reflection
      .addService(ProtoReflectionService.newInstance())
      .addService(new HealthStatusManager().getHealthService)
      .addService(orderServiceD)
      .addService(helloServiceD)

    // fs2.grpc.syntax
    nsBuilder.resource[IO]
  }

  val grpcServer: Resource[IO, Server] =
    for {
      os <- orderService
      hs <- helloService
      sv <- runServer(os, hs)
    } yield sv

}
