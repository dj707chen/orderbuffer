package com.rockthejvm

import cats.effect.*
import com.rockthejvm.protos.orders.*
import fs2.Stream
import fs2.grpc.syntax.all.*
import io.grpc.*
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder
import io.grpc.protobuf.services.*

// Implement the defined service
class OrderService extends OrderFs2Grpc[IO, Metadata] {
  override def sendOrderStream(
      request: Stream[IO, OrderRequest],
      ctx: Metadata,
  ): Stream[IO, OrderReply] = {
    request.map { orderReq =>
      OrderReply(
        orderReq.orderid,
        orderReq.items,
        orderReq.items.map(i => i.amount * i.qty).reduce(_ + _),
      )
    }
  }
}
