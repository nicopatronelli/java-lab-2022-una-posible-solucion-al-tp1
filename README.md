# java-lab-2022-una-posible-solucion-al-tp1
Código de una posible solución al TP N°1. 

Nota: Actualmente, la misma no contempla el caso en que una transferencia falle porque falla el depósito, es decir, 
las cuentas pueden quedar en un estado inconsistente. Para solucionar dicha situación hay que implementar un mecanismo
de rollback en caso de fallo en el depósito de dinero (la segunda parte de una transferencia), para devolver el dinero 
a la cuenta origen a la que ya retiramos el monto de la transferencia, o bien realizar todas las validaciones necesarias
antes de iniciar con el traspaso de dinero.

