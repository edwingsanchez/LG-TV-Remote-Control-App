# Política de Privacidad de LG TV Controller

**Última actualización:** 26 de septiembre de 2026

La presente Política de Privacidad describe cómo **LG TV Controller** ("nosotros", "nuestra aplicación" o "el servicio"), identificada en Google Play Store con el nombre de paquete `com.github.edwingsanchez.lgtvcontroller`, maneja la información y protege la privacidad de los usuarios.

Nos tomamos muy en serio la privacidad de nuestros usuarios. Esta aplicación está diseñada para funcionar de manera **local**, sin recopilar, almacenar ni transmitir datos personales a servidores externos.

---

## 1. Información que recopilamos (o que NO recopilamos)

### A. Información Personal y Sensible
**No recopilamos ningún tipo de información de identificación personal** (PII). 
* No solicitamos ni recopilamos nombres, direcciones de correo electrónico, números de teléfono, ubicación geográfica precisa, contactos, fotos, documentos ni datos financieros.
* No requerimos la creación de una cuenta de usuario para utilizar la aplicación.

### B. Datos de Dispositivos y Red Local
Para cumplir con su función principal (controlar su televisor inteligente LG WebOS), la aplicación busca y se conecta a televisores dentro de su red Wi-Fi local:
* **Información de la TV:** La app puede leer la dirección IP local del televisor, el nombre del modelo y las claves/tokens de emparejamiento generadas durante la vinculación.
* **Uso exclusivo en red local:** Esta información se utiliza **únicamente dentro de su red de área local (LAN)** para establecer comunicación directa con su televisor LG.

### C. Almacenamiento de Datos en el Dispositivo
* Toda la configuración preferida, la lista de televisores guardados/favoritos y los tokens de emparejamiento se almacenan **únicamente en la memoria interna de su dispositivo móvil** (a través de la base de datos local de la app).
* **Ninguno** de estos datos se envía a servidores en la nube ni a terceros.

---

## 2. Permisos del Dispositivo y su Justificación

Para funcionar correctamente, la aplicación solicita únicamente los permisos de Android necesarios para operar en la red Wi-Fi local:

| Permiso | Propósito |
| :--- | :--- |
| `android.permission.INTERNET` | Permite que la app envíe comandos (a través de protocolos HTTP/WebSocket) al televisor LG en la red local. |
| `android.permission.ACCESS_NETWORK_STATE` | Permite verificar si el dispositivo móvil tiene una conexión de red activa antes de intentar conectarse a la TV. |
| `android.permission.ACCESS_WIFI_STATE` | Permite verificar el estado de la conexión Wi-Fi local. |
| `android.permission.CHANGE_WIFI_MULTICAST_STATE` | Permite la detección automática de televisores LG disponibles en la red local mediante protocolos de descubrimiento (SSDP/Multicast). |

---

## 3. Servicios y Herramientas de Terceros

* **Sin anuncios ni redes publicitarias:** La aplicación no incluye bibliotecas de publicidad (como Google AdMob u otras redes de anuncios).
* **Sin analítica de terceros:** No utilizamos herramientas de seguimiento o analítica de comportamiento (como Google Analytics para Firebase, Mixpanel, etc.).
* **Librerías de Código Abierto:** La aplicación utiliza bibliotecas estándar de Android y componentes de código abierto para la interfaz de usuario y la comunicación local (ej. ConnectSDK / Glide para la carga local de íconos de canales o aplicaciones transmitidos por el televisor). Ninguna de estas bibliotecas transmite datos personales fuera de su red local.

---

## 4. Retención y Eliminación de Datos

* Puesto que todos los datos se almacenan de forma strictly local en su dispositivo Android, usted tiene el control total sobre ellos.
* Puede eliminar todos los datos almacenados en cualquier momento borrando el almacenamiento/caché de la aplicación desde la **Configuración de Android > Aplicaciones > LG TV Controller > Almacenamiento > Borrar datos**, o simplemente desinstalando la aplicación.

---

## 5. Privacidad de los Niños (COPPA / GDPR)

Nuestra aplicación no está dirigida explícitamente a menores de 13 años (o la edad mínima legal en su jurisdicción), ni recopila a sabiendas información de niños. Al no recopilar ningún tipo de datos personales, la aplicación cumple con las regulaciones de protección de menores en línea.

---

## 6. Seguridad de la Información

Dado que la comunicación se realiza exclusivamente entre su dispositivo móvil y su televisor dentro de su propia red Wi-Fi privada, la seguridad de la transmisión depende de la seguridad de su red local.

---

## 7. Cambios a esta Política de Privacidad

Podemos actualizar nuestra Política de Privacidad periódicamente para reflejar cambios en la aplicación o requisitos legales. Cualquier actualización se publicará con una nueva fecha de "Última actualización".

---

## 8. Contacto

Si tiene alguna pregunta o duda con respecto a esta Política de Privacidad o el manejo de datos en la aplicación, puede ponerse en contacto con nosotros a través de:

* **Correo electrónico de soporte:** `[TU_CORREO_DE_SOPORTE@DOMINIO.COM]`
* **Desarrollador:** Edwing Sánchez
