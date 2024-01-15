#!/usr/bin/env bash

# For examples, refer https://github.com/fullstorydev/grpcurl

grpcurl -vv -plaintext -d @ localhost:9999 lab.Greeter/SayHello <<'EOM'
    {
        "name": "DJ"
    }
EOM
