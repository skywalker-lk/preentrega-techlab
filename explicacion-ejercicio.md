**Talento Tech Buenos Aires — Back-End Java**
**Preentrega de proyecto**

## Entregable
Se solicita que diseñes un sistema básico que permita gestionar información inicial sobre los productos de empresa.

## Explicacion del desarrollo para pre-entrega del proyecto.

---
Al principio se complico un poco como organizar los paquetes dentro de src, tenía muchos niveles de anidamiento, 
entonces se me hizo un poco más simple trabajarlo desde src/ e ir armando las carpetas que fui necesitando ó que pense 
que iba a necesitar. El problema fue que al inicio el editor no me reconocia los paquetes correctamente,
le di a reorganizar pensando que reconectar bien, pero lo que hizo fue crear más anidaciones dentro una estructura,
que quedo de la siguiente manera: src/com/techlab/ecommerce/com/techlab/ecommerce/ etc.,
por eso modifique todo comence a armar todo en la carpeta src/ y una vez que tuve listo el proyecto lo organize en las 
carpetas como se debia entregar.
---
Primero comence a modificar el módulo de Productos que es el que se venía desarrollando en las clases prácticas del 
curso de Java, a partir de ahi comence realizar la busqueda por nombre que me faltaba, que era muy parecida 
a la búsqueda por ID que ya estaba implementada, mediante un menu elije uno y otro.
---
Después continue con la creacion de bebida y comida, luego pase producto a una carpeta para organizar un poco, a partir 
de ahi que herencia de producto, creo que me faltaba litros y peso para separar un poco de producto,
y agregue el eliminar producto que faltaba, con una pequeña confirmación.
---
Segui armando las excepciones que las arme todas igual, copiando las excepciones que ya implementamos en otros casos.

Luego segui con el módulo de pedidos, primero armando los modelos Pedido y LineaPedido, después el service que los 
utiliza, esa fue la manera que utilize, para otros modulos que quise agregar como Usuario o Categoria, pensando un 
listado de productos un poco mas completo. Al inicio del módulo pense en crear un Cliente, 
después termine dejando un String para que más facil para instanciar el pedido, también para el pedido agregue fecha, 
entonces busque en internet como validar e insertar una fecha desde escaner, 
después me di cuenta que tenía un error porque generaba una fecha con new Date(), y entonces pense dar al 
usuario la posibilidad de agregar una fecha anterior o posterior para los nuevos pedidos. Esa funcion leerFecha 
lo deje en util.
---
Por último termine haciendo unos menus extra para pedidos solamente, para usuarios y para categorias. 
Pensando un poco como lo puedo avanzar luego, quiero que para usuario cliente se guarde en el pedido, pero usuario de 
carga los pedidos y administrador para administrar los productos, categorias y subcategorias.
Categoria primero era una clase Bebida y Comida, después lo arme como un enum y dentro de cada categoria se pueda crear
una subcategoria nueva, no se si es la mejor manera pero me quedo más fácil de entender.

Postdata: deje modulo de usuario no lo termine integrando para no romper todo.
Mi idea era implementar una clase Gerente que pueda ver los datos de los pedidos realizados, y poder buscar esta información 
filtrando mediante fecha desde y fecha hasta, tipo un tablero para un dashboard.
