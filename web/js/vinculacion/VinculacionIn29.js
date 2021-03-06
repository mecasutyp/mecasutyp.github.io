var c_nivel, c_tipo;

function guardar29(){
    if ($("form[name='VinculacionIn29Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        //$().alerta("rojo","Error al guardar. Por favor verifique los datos");
        enviarDatosIndicadores('Error','VinculacionIn29Datos','VinculacionIn29Form');
    }else{
        var cadena = ":";
        $('form[name="VinculacionIn29Form"] input[name^="val"][name$="1_1"]').each(function(index, domEle){
            var separador = domEle.name.indexOf(","); //Obtiene el índice para sacar una subcadena con los ID's de la base de datos
            var x = domEle.name.substring(3, separador); //Obtiene una subcadena con los ID's de la base de datos, el número 3 indica el prefijo 'val' asignado automáticamente en el jsp
            var v_estudio = x;
            //Concatena en los valores obtenidos en el paso anterior los valores de los 3 periodos introducidos por el capturista para hacer la insercion en la base de datos
            if(v_estudio == -1){
                x = x.concat(",",
                    $(domEle).val(),",",
                    $('form[name="VinculacionIn29Form"] input[name="val'+v_estudio+',2_1"]').val(),",",
                    $('form[name="VinculacionIn29Form"] input[name="val'+v_estudio+',3_1"]').val(),",0,0");
            }else{
                x = x.concat(",",
                    $(domEle).val(),",",
                    $('form[name="VinculacionIn29Form"] input[name="val'+v_estudio+',2_1"]').val(),",",
                    $('form[name="VinculacionIn29Form"] input[name="val'+v_estudio+',3_1"]').val(),",",
                    $('form[name="VinculacionIn29Form"] input[name="th'+v_estudio+'_1"]').val()
                    
                 );
            }
            cadena = cadena.concat(x,":"); //Se concatena un ':' para separar los valores de cada consulta
        });     
        enviarDatosIndicadores(cadena,'VinculacionIn29Datos','VinculacionIn29Form');
        //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='VinculacionIn29Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "29";
                GuardarComentarios(comentario, indicador);
            }
        //retrieveURL('/VinculacionIn29.msut?valores='+cadena, 'VinculacionIn29Form');
       // $().alerta("verde","Datos Guardados Correctamente.");
    }
}

function indicador29(control){
    var nombreControl = control.name;
    c_nivel = nombreControl.substring(3,nombreControl.indexOf(","));
    nombreControl = nombreControl.substring(nombreControl.indexOf(",") + 1, nombreControl.length);
    c_tipo = nombreControl.substring(0,nombreControl.indexOf("_"));
    if (control.value == "")
        control.value = 0;

    if (!/^([0-9])*$/.test(control.value)){
        $().alerta("rojo","El valor '" + control.value + "' no es v&aacute;lido, debe ser un n&uacute;mero entero.");
        $(control).addClass("inputerror").removeClass("inputok");
        return;
    }else{
        if(control.value == 0){
            $("form[name='VinculacionIn29Form'] input[name='"+control.name.substring(0,control.name.length-1)+"2']").attr("disabled","disabled").val(0);
        }else{
            $("form[name='VinculacionIn29Form'] input[name='"+control.name.substring(0,control.name.length-1)+"2']").removeAttr("disabled");
        }
        $(control).val(Number(control.value));
        $(control).addClass("inputok").removeClass("inputerror");
        cargarDatos29();
    }
}

function cargarDatos29(){
    $('form[name="VinculacionIn29Form"] input[name="th'+c_nivel+'_1"]').val(0);
    $('form[name="VinculacionIn29Form"] input[name="tv'+c_tipo+'_1"]').val(0);
    $('form[name="VinculacionIn29Form"] input[name="tv4_1"]').val(0);
    $('form[name="VinculacionIn29Form"] input[name^="val'+c_nivel+',"][name$="_1"]').each(function(index, domEle) {
        $('form[name="VinculacionIn29Form"] input[name="th'+c_nivel+'_1"]').val(parseFloat($('form[name="VinculacionIn29Form"] input[name="th'+c_nivel+'_1"]').val())+parseFloat($(domEle).val()));
    });
    
    $('form[name="VinculacionIn29Form"] input[name^="val"][name$="'+c_tipo+'_1"]').each(function(index, domEle) {
        $('form[name="VinculacionIn29Form"] input[name="tv'+c_tipo+'_1"]').val(parseFloat($('form[name="VinculacionIn29Form"] input[name="tv'+c_tipo+'_1"]').val())+parseFloat($(domEle).val()));
    });
    $('form[name="VinculacionIn29Form"] input[name^="th"][name$="_1"]').each(function(index, domEle) {
        $('form[name="VinculacionIn29Form"] input[name="tv4_1"]').val(parseFloat($('form[name="VinculacionIn29Form"] input[name="tv4_1"]').val())+parseFloat($(domEle).val()));
    });
    
    var totporc=0;
    for(var a = -1; a <= 10; a++){
       var eec0=getDecimal(($('form[name="VinculacionIn29Form"] input[name="th'+a+'_1"]').val() /
            $('form[name="VinculacionIn29Form"] input[name="tv4_1"]').val()) *100);                 
        if(isNaN(eec0)){
            eec0=0;
        }    
        totporc+=eec0;
        $('form[name="VinculacionIn29Form"] input[name="eec-'+a+'_1"]').val(eec0 + " %");
        var eec4=getDecimal(($('form[name="VinculacionIn29Form"] input[name="th-1_1"]').val() /
            $('form[name="VinculacionIn29Form"] input[name="tv4_1"]').val()) *100);                 
        if(isNaN(eec4)){
            eec4=0;
        }    
       
        $('form[name="VinculacionIn29Form"] input[name="eec-4_1"]').val(eec4+ " %");
    }
    
    $('form[name="VinculacionIn29Form"] input[name="tv5_1"]').val(getDecimal(totporc)+ " %"); 
    
}

function calcularporcen(control, numero){
    if (control.value == "")
        control.value = 0;

    if (!/^([0-9])*$/.test(control.value)){
        $().alerta("rojo","El valor '" + control.value + "' no es v&aacute;lido, debe ser un n&uacute;mero entero.");
        $(control).addClass("inputerror").removeClass("inputok");
        return;
    } else{
     
    }
}
function blurVal29(){
    $('form[name="VinculacionIn29Form"] input[name^="val"]').each(function(index, domEle) {
        if($(domEle).val() != "0"){
            $(domEle).blur();
        }
    });
}