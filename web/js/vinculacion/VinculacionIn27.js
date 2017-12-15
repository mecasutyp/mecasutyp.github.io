function calcularCadena27(){
    var cadena = "";
    cadena = cadena.concat(
        $("form[name='VinculacionIn27Form'] input[name='valSer']").val().replace(/,/g,""), ",",
        $("form[name='VinculacionIn27Form'] input[name='valCol']").val().replace(/,/g,""), ",",
        $("form[name='VinculacionIn27Form'] input[name='valDif']").val().replace(/,/g,"")
        );
    return cadena;
}

function guardar27(){
    var cadena="";
    if ($("form[name='VinculacionIn27Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        //$().alerta("rojo","Error al guardar. Por favor verifique los datos");
        cadena="Error";
    }else{
        cadena=calcularCadena27();
        //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='VinculacionIn27Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "27";
                GuardarComentarios(comentario, indicador);
            }
    //retrieveURL('/VinculacionIn27.msut?valores='+calcularCadena27(), 'VinculacionIn27Form');
    //$().alerta("verde","Datos Guardados Correctamente.");
    }
    return cadena;
}

function indicador27(control){
    if(!/^\d+(\.\d{1,2})?$/.test(control.value.replace(/,/g,""))){
        $().alerta("rojo","El valor '" + control.value + "' no es v&aacute;lido, \n\
                    debe ser un n&uacute;mero equivalente a una cantidad monetaria.");  
        $(control).addClass("inputerror").removeClass("inputok");
        $(control).val(0)
        return;
   }else {
       $(control).val(getDecimal(control.value.replace(/,/g,"")));
       $(control).removeClass("inputerror").addClass("inputok");
       cargarDatos27();
       formatearnumero(control);
   }
   
}

function cargarDatos27(){
    
    //Se recuperan valores
    var valSer = parseFloat($("form[name='VinculacionIn27Form'] input[name='valSer']").val().replace(/,/g,""));
    var valCol = parseFloat($("form[name='VinculacionIn27Form'] input[name='valCol']").val().replace(/,/g,""));
    var valDif = parseFloat($("form[name='VinculacionIn27Form'] input[name='valDif']").val().replace(/,/g,""));
    var presTot = parseFloat($("form[name='VinculacionIn27Form'] input[name='presTotalAut']").val().replace(/,/g,""));
    var totIngPro = valSer + valCol+ valDif;

    //Se formatean cantidades
    $("form[name='VinculacionIn27Form'] input[name='valSer']").val(accounting.formatNumber(valSer,0,","));
    $("form[name='VinculacionIn27Form'] input[name='valCol']").val(accounting.formatNumber(valCol,0,","));
    $("form[name='VinculacionIn27Form'] input[name='valDif']").val(accounting.formatNumber(valDif,0,","));
    $("form[name='VinculacionIn27Form'] input[name='totIngPro']").val(accounting.formatNumber(totIngPro,0,","));
    $("form[name='VinculacionIn27Form'] input[name='presTotalAut']").val(accounting.formatNumber(presTot,0,","));
    
   if(presTot != 0){
        $("form[name='VinculacionIn27Form'] input[name='porcSer']").val(getDecimal((valSer/presTot)*100) + " %");
        $("form[name='VinculacionIn27Form'] input[name='porcCol']").val(getDecimal((valCol/presTot)*100) + " %");
        $("form[name='VinculacionIn27Form'] input[name='porcDif']").val(getDecimal((valDif/presTot)*100) + " %");
        $("form[name='VinculacionIn27Form'] input[name='porcTot']").val(getDecimal((totIngPro/presTot)*100) + " %");
    }else{
        $("form[name='VinculacionIn27Form'] input[name^='porc']").val(0);
    }
}

function formatearnumero(control)
{
    $(control).val(accounting.formatNumber(control.value, 0, ","));
} 