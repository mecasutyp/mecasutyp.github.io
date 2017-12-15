function noControles36(){
    var i = 0;
    while(document.forms["EquidadIn36Form"][i].name != "fin"){
        i++;
    }
    return i;
}

function validarGuardado36(){
    if($('form[name="EquidadIn36Form"] input').hasClass('inputerror')){
        return "Error";
    } else {
        return guardar36();
    }
}
function guardar36(){
    var cadena = "-";
    var v2;
    for(i = 0; i < noControles36(); i++){
        if (document.forms["EquidadIn36Form"][i].name.indexOf("val") != -1){
            var mes = document.forms["EquidadIn36Form"][i].name.indexOf("_"); //Obtiene el índice para sacar una subcadena con los ID's de la base de datos
            var x = document.forms["EquidadIn36Form"][i].name.substring(3, mes); //Obtiene una subcadena con los ID's de la base de datos, el número 3 indica el prefijo 'val' asignado automáticamente en el struts form
            x = x.concat(",", document.forms["EquidadIn36Form"][i].value); //Concatena en los valores obtenidos en el paso anterior los valores de los 3 periodos introducidos por el capturista para hacer la insercion en la base de datos
            if(i==0){
                v2=document.forms["EquidadIn36Form"][i+1].value;
            }
            if(v2!=0 && /^([0-9])*$/.test(document.forms["EquidadIn36Form"][i].value)){
                v3 = (document.forms["EquidadIn36Form"][i].value/v2)*100;
                v3=getDecimal(v3);
            }else{
                v3=0;
            }
            if(i==0){
                document.forms["EquidadIn36Form"][i+2].value = v3 + " %";//Suma los valores y asigna el total al último control de la fila
                i+=2;
            }else{
                document.forms["EquidadIn36Form"][i+1].value = v3 + " %";
                i++;
            }
            cadena = cadena.concat(x,"-"); //Se concatena un '-' para separar los valores de cada consulta
            //i++;//Aumentamos el iterador 2 para brincar los numeros equivalentes a los periodos enero-abril y mayo-agosto ya que los valores fueron recuperados en esta iteración
        }
    }
    //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='EquidadIn36Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "36";
                GuardarComentarios(comentario, indicador);
            }
    return cadena;
}

function indicador36(control){

    var controles = new Array();
    var j = 0;

    while(document.forms["EquidadIn36Form"][j].name != "fin"){
        controles.push(document.forms["EquidadIn36Form"][j].name);
        j++;
    }

    if (control.value == "")
        control.value = 0;

    if (!/^([0-9])*$/.test(control.value)){
        $().alerta("amarillo", "El valor '".concat(control.value).concat("' no es v&aacute;lido"));
        $(control).addClass("inputerror").removeClass("inputok");
        cargarDatos36();
    }else{
        /*Modificar a partir de acá*/
        control.value=Number(control.value);
        cargarDatos36();
        /*Hasta aqui!*/

        $(control).addClass("inputok").removeClass("inputerror");
    }

    if ($('form[name="EquidadIn36Form"] input').hasClass("inputerror")){
        $('li[class*="ui-tabs-selected ui-state-active"]').globo("update", "<center>Indicador con <br/> errores, favor <br/> de revisar.</center>").globo("show");
    }else{
        $('li[class*="ui-tabs-selected ui-state-active"]').globo("hide");
    }
}

function cargarDatos36(){
    guardar36();
    var v1 = 0, v2 = 0, v3 = 0;
    for(i = 0; i < noControles36(); i++){
        if (document.forms["EquidadIn36Form"][i].name.indexOf("tv") == -1){
            v1+=parseInt(document.forms["EquidadIn36Form"][i].value);
            if(i==0){
                v2=parseInt(document.forms["EquidadIn36Form"][i+1].value);
                i++;
            }
            i++;
        }else{
            if(!/^([0-9])*$/.test(v1)){
                v1=0;
            }
            document.forms["EquidadIn36Form"][i].value = v1;
            document.forms["EquidadIn36Form"][i+1].value = v2;
            if(v2!=0 && /^([0-9])*$/.test(v2) && /^([0-9])*$/.test(v1)){
                v3 = (v1/v2)*100;
                v3=getDecimal(v3);
            }else{
                v3=0;
            }
            document.forms["EquidadIn36Form"][i+2].value = v3 + " %";
            v1 = 0;
            v2 = 0;
            v3 = 0;
            i+=2;
        }
    }
}