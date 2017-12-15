function guardar30(){
    var cadena= "";
    if ($("form[name='VinculacionIn30Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
       // $().alerta("rojo","Error al guardar. Por favor verifique los datos");
        cadena="Error";
    }else{
        cadena = cadena.concat(
            $("form[name='VinculacionIn30Form'] input[name='val1']").val(), ",",
            $("form[name='VinculacionIn30Form'] input[name='val2']").val(), ",",
            $("form[name='VinculacionIn30Form'] input[name='val3']").val(), ",",
            $("form[name='VinculacionIn30Form'] input[name='val4']").val(), ",",
            $("form[name='VinculacionIn30Form'] input[name='val5']").val(), ",",
            $("form[name='VinculacionIn30Form'] input[name='val6']").val()
            );
    //retrieveURL('/VinculacionIn30.msut?valores='+cadena, 'VinculacionIn30Form');
    //$().alerta("verde","Datos Guardados Correctamente.");
    }
    enviarDatosIndicadores(cadena,'VinculacionIn30Datos','VinculacionIn30Form');
    //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='VinculacionIn30Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "30";
                GuardarComentarios(comentario, indicador);
            }
}

function indicador30(control){
    if (control.value == "")
        control.value = 0;

    if (!/^([0-9])*$/.test(control.value)){
        $().alerta("rojo","El valor '" + control.value + "' no es v&aacute;lido, debe ser un n&uacute;mero entero.");
        $(control).addClass("inputerror").removeClass("inputok");
        return;
    }else{
        $(control).val(Number(control.value));
        $(control).addClass("inputok").removeClass("inputerror");
        if (!$("form[name='VinculacionIn30Form'] input").hasClass("inputerror")){
            operacionesVinculacion30();
        }      
        
    }
}
function operacionesVinculacion30(){
    //Horizontales
    $('form[name="VinculacionIn30Form"] input[name="th1"]').val(
        parseInt($('form[name="VinculacionIn30Form"] input[name="val1"]').val()) + parseInt($('form[name="VinculacionIn30Form"] input[name="val2"]').val()) +
        parseInt($('form[name="VinculacionIn30Form"] input[name="val3"]').val()));
    $('form[name="VinculacionIn30Form"] input[name="th2"]').val(
        parseInt($('form[name="VinculacionIn30Form"] input[name="val4"]').val()) + parseInt($('form[name="VinculacionIn30Form"] input[name="val5"]').val()) +
        parseInt($('form[name="VinculacionIn30Form"] input[name="val6"]').val()));            
    //Verticales
    //Columna 1
    $('form[name="VinculacionIn30Form"] input[name="tv1"]').val( parseInt($('form[name="VinculacionIn30Form"] input[name="val1"]').val()) +
        parseInt($('form[name="VinculacionIn30Form"] input[name="val4"]').val()));
    //Columna 2
    $('form[name="VinculacionIn30Form"] input[name="tv2"]').val(parseInt($('form[name="VinculacionIn30Form"] input[name="val2"]').val()) +
        parseInt($('form[name="VinculacionIn30Form"] input[name="val5"]').val()));
    //Columna 3
    $('form[name="VinculacionIn30Form"] input[name="tv3"]').val(parseInt($('form[name="VinculacionIn30Form"] input[name="val3"]').val()) +
        parseInt($('form[name="VinculacionIn30Form"] input[name="val6"]').val()));
    //Columna 4        
    $('form[name="VinculacionIn30Form"] input[name="tv4"]').val(parseInt($('form[name="VinculacionIn30Form"] input[name="th1"]').val()) +
        parseInt($('form[name="VinculacionIn30Form"] input[name="th2"]').val()));   
    if(parseInt($('form[name="VinculacionIn30Form"] input[name="tv4"]').val())>0){   
        $('form[name="VinculacionIn30Form"] input[name="th%1"]').val(getDecimal(
            (parseFloat($('form[name="VinculacionIn30Form"] input[name="th1"]').val())/parseFloat($('form[name="VinculacionIn30Form"] input[name="tv4"]').val())*100)) + " %");
    
        $('form[name="VinculacionIn30Form"] input[name="th%2"]').val(getDecimal(
            (parseFloat($('form[name="VinculacionIn30Form"] input[name="th2"]').val())/parseFloat($('form[name="VinculacionIn30Form"] input[name="tv4"]').val())*100)) + " %");
    }else{       
        $('form[name="VinculacionIn30Form"] input[name="th%1"]').val(0);
        $('form[name="VinculacionIn30Form"] input[name="th%2"]').val(0);
    }
    $('form[name="VinculacionIn30Form"] input[name="tv5"]').val(parseFloat($('form[name="VinculacionIn30Form"] input[name="th%1"]').val())+parseFloat($('form[name="VinculacionIn30Form"] input[name="th%2"]').val()) + " %");
}
