package com.rockthejvm

import cats.Applicative
import cats.syntax.applicative.*
import lab.hello.*

// Implement the defined service
class GreeterService[F[_]: Applicative, Metadata] extends GreeterFs2Grpc[F, Metadata] {
  def sayHello(req: lab.hello.HelloRequest, ctx: Metadata): F[lab.hello.HelloResponse] =
    HelloResponse(s"Hello, ${req.name}!", happy = true).pure[F]
}
