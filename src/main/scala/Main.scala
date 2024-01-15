package com.rockthejvm

import cats.effect.*
import cats.syntax.parallel.*
import com.comcast.ip4s.*
import com.rockthejvm.routes.AppRoutes.restService
import com.rockthejvm.Server.grpcServer
import org.http4s.ember.server.EmberServerBuilder

object Main extends IOApp {
  private def httpServerStream: IO[Nothing] =
    EmberServerBuilder
      .default[IO]
      .withHost(host"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(restService.orNotFound)
      .build
      .use(_ => IO.never)

  def run(args: List[String]): IO[ExitCode] =
    (
      httpServerStream,
      grpcServer
        .evalMap(svr => IO(svr.start()))
        .useForever,
    )
      .parMapN((http, grpc) => ())
      .as(ExitCode.Success)
}
