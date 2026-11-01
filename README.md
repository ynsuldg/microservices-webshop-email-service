# Email Service

## Ansvar

Email Service tar emot ordermeddelanden från RabbitMQ-kön `new-queue` och skickar orderbekräftelser via SMTP.
I betalningsflödet publicerar order-service meddelandet efter verifierad betalning.
Ordermejlet visar USD, samma valuta som Stripe Checkout.

## Tekniker

Java 17, Spring Boot 4, Spring AMQP, Spring Mail, RabbitMQ, Maven och spring-messenger.

## Databas

Tjänsten har ingen egen databas.

## Miljövariabler

| Variabel | Lokalt och Render |
| --- | --- |
| `RABBIT_HOST`, `RABBIT_PORT` | Brokeradress och TLS-port. |
| `RABBIT_USERNAME`, `RABBIT_PASSWORD`, `RABBIT_VIRTUAL_HOST` | Inloggning och virtual host för samma broker som order-service. |
| `MAIL_HOST`, `MAIL_PORT` | SMTP-server och port med STARTTLS-stöd. |
| `MAIL_ACCOUNT`, `MAIL_PASSWORD` | SMTP-inloggning, till exempel leverantörens applösenord. |

Variabelnamnen börjar med `RABBIT_` här, medan order-service använder `RABBITMQ_`.
Båda måste ansluta till samma broker och virtual host. Könamnet `new-queue` är fast i email-service.
Tjänsten behöver inga Stripe-nycklar eller `INTERNAL_SERVICE_TOKEN`. Hemliga värden ska inte sparas i Git.

## Starta applikationen lokalt

Sätt miljövariablerna och starta från projektmappen.

**macOS/Linux:**

```bash
./mvnw spring-boot:run
```

**Windows PowerShell:**

```powershell
.\mvnw.cmd spring-boot:run
```

Kräver Java 17 eller senare, tillgång till RabbitMQ och ett SMTP-konto.
Maven-beroendet `spring-messenger:1.0.5` hämtas från GitHub Packages och kan kräva
åtkomst via Maven-inställningar. CI använder `PACKAGES_TOKEN` med paketläsbehörighet.
Lokal port: 8083.

## Render

| Tjänst | Render-adress |
| --- | --- |
| Frontend – öppna webshoppen | [microservices-webshop-frontend.onrender.com](https://microservices-webshop-frontend.onrender.com) |
| Auth Service | [microservices-webshop-auth-service.onrender.com](https://microservices-webshop-auth-service.onrender.com) |
| Product Service | [microservices-webshop-product-service.onrender.com](https://microservices-webshop-product-service.onrender.com) |
| Order Service | [microservices-webshop-order-service.onrender.com](https://microservices-webshop-order-service.onrender.com) |
| Email Service | [microservices-webshop-email-service.onrender.com](https://microservices-webshop-email-service.onrender.com) |
| Gateway Service | [microservices-webshop-gateway-service.onrender.com](https://microservices-webshop-gateway-service.onrender.com) |
| Payment Service | [microservices-webshop-payment-service.onrender.com](https://microservices-webshop-payment-service.onrender.com) |
- Docker-image: `ynsuldg/microservices-webshop-email-service:1.0.0`.

Använd **Web Service → Existing Image**, sätt miljövariablerna i Render och behåll Docker-imagens startkommando.
Efter en ny image från senaste `main` väljs **Manual Deploy → Deploy latest reference**.
En äldre Actions-omkörning kan skriva över samma tagg med äldre kod.

Email-service är en meddelandekonsument, inte en användarsida. Render-adressens rot behöver inte visa någon sida.
Render Free kan ge väntetid när tjänsterna väcks. Se [Render-dokumentationen](https://render.com/docs/deploying-an-image).

**Render Free blockerar utgående SMTP på port 25, 465 och 587.** Att tjänsten är Live innebär därför
inte att mejl kan skickas. För SMTP på 465/587 behövs en betald instans eller en annan e-postlösning.
Port 25 blockeras även för betalda tjänster. Se [Render Free-begränsningar](https://render.com/docs/free).

## Relaterade repositories

- [Auth Service](https://github.com/ynsuldg/microservices-webshop-auth-service)
- [Product Service](https://github.com/ynsuldg/microservices-webshop-product-service)
- [Order Service](https://github.com/ynsuldg/microservices-webshop-order-service)
- [Gateway Service](https://github.com/ynsuldg/microservices-webshop-gateway-service)
- [Payment Service](https://github.com/ynsuldg/microservices-webshop-payment-service)
- [Frontend](https://github.com/ynsuldg/microservices-webshop-frontend)
