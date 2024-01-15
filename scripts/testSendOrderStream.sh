#!/usr/bin/env bash

# For examples, refer https://github.com/fullstorydev/grpcurl

grpcurl -vv -plaintext -d @ localhost:9999 com.rockthejvm.protos.Order/SendOrderStream << 'EOM'
    {
        "items": [
            {
                "amount": "100",
                "name": "aliquip ut veniam",
                "qty": 2
            }
        ],
        "orderid": 2133283586
    }
EOM
