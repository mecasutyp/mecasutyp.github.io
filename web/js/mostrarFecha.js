function MostrarFecha(input){
    var fecha_actual = new Date();
    dia_mes = fecha_actual.getDate(); //dia del mes
    mes = fecha_actual.getMonth() + 1;
    anio = fecha_actual.getFullYear();
    //si el mes viene con un digito, le pone el cero
    if (mes.toString().length <= 1){
        mes = "0".concat(mes.toString());
    }
    //si el dia_mes viene con un digito, le pone el cero
    if (dia_mes.toString().length <= 1){
        dia_mes = "0".concat(dia_mes.toString());
    }
    //escribe en input
    document.getElementById(input).value = anio + "-" + mes + "-" + dia_mes;
}

/*Ciclo infinito para cambiar la fecha del ingreso de una nueva universidad
 * para mantener el html input
 */

function cambiarFecha(){
    setTimeout(function(){
        if (document.getElementById("fechaAcreditacion").value !=
            document.getElementById("fechaAcred").value){
                document.getElementById("fechaAcreditacion").value=
                    document.getElementById("fechaAcred").value;
                cambiarFecha();
        }
    },1000);
}