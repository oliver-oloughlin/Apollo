FROM frolvlad/alpine-glibc:alpine-3.11_glibc-2.31

RUN apk update && apk add curl

RUN curl -fsSL https://deno.land/x/install/install.sh | sh && mv /root/.deno/bin/deno /bin/deno

ENTRYPOINT ["deno"]

CMD ["run", "-A", "https://raw.githubusercontent.com/oliver-oloughlin/Apollo/development/Consumer/main.ts"]