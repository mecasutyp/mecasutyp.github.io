function calcularCadena26(){
    var cadena = "";
    cadena = cadena.concat(
        $("form[name='VinculacionIn26Form'] input[name='valConv']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valConvSup']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valPub']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valPri']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valSoc']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valAlumno']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valDocentes']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valConvIn']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valConvSupIn']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valPubIn']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valPriIn']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valSocIn']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valAlumnoIn']").val(), ",",
        $("form[name='VinculacionIn26Form'] input[name='valDocenteIn']").val()
        );
            
    return cadena;
}

function guardar26(){
    var cadena="";
    verificarceros();
    if ($("form[name='VinculacionIn26Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        //$().alerta("rojo","Error al guardar. Por favor verifique los datos");
        cadena="Error";
    }
    else{
      cadena=calcularCadena26();
      //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='VinculacionIn26Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "26";
                GuardarComentarios(comentario, indicador);
            }
    //retrieveURL('/VinculacionIn26.msut?valores='+calcularCadena26(), 'VinculacionIn26Form');
    //$().alerta("verde","Datos Guardados Correctamente.");
    }
    return cadena;
}
function verificarceros(){
    
    if($("form[name='VinculacionIn26Form'] input[name='totOrgVinc']").val() <= 0){
        $().alerta("rojo","El Total de Organismos vinculados acumulados del Cuadro 26.1.2 debe ser mayor a 0");
        $("form[name='VinculacionIn26Form'] input[name='totOrgVinc']").addClass("inputerror").removeClass("inputok");
    }else{
        $("form[name='VinculacionIn26Form'] input[name='totOrgVinc']").addClass("inputok").removeClass("inputerror");
    }
//  SE VALIDA QUE LOS ORGANISMOS VINCULADOS INTERNACIONALES SEAN MAYOR A CERO
//      if($("form[name='VinculacionIn26Form'] input[name='totOrgVincIn']").val() <= 0){
//        $().alerta("rojo","El Total de Organismos vinculados acumulados del Cuadro 26.2.2 debe ser mayor a 0");
//        $("form[name='VinculacionIn26Form'] input[name='totOrgVincIn']").addClass("inputerror").removeClass("inputok");
//    }else{
//        $("form[name='VinculacionIn26Form'] input[name='totOrgVincIn']").addClass("inputok").removeClass("inputerror");
//    }
    if($("form[name='VinculacionIn26Form'] input[name='valConv']").val() <= 0){
        //$().alerta("rojo","El N&uacute;mero de convenios firmados acumulados al ciclo escolar del Cuadro 26.1.1 debe ser mayor a 0");
        $("form[name='VinculacionIn26Form'] input[name='valConv']").addClass("inputerror").removeClass("inputok");
    }else{
        $("form[name='VinculacionIn26Form'] input[name='valConv']").addClass("inputok").removeClass("inputerror");
    }
    if($("form[name='VinculacionIn26Form'] input[name='valConvSup']").val() <= 0){
        $("form[name='VinculacionIn26Form'] input[name='valConvSup']").addClass("inputerror").removeClass("inputok");
    }else{
        $("form[name='VinculacionIn26Form'] input[name='valConvSup']").addClass("inputok").removeClass("inputerror");
    }
//  SE VALIDA QUE LOS ORGANISMOS VINCULADOS INTERNACIONALES SEAN MAYOR A CERO
//    if($("form[name='VinculacionIn26Form'] input[name='valConvIn']").val() <= 0){
//       $("form[name='VinculacionIn26Form'] input[name='valConvIn']").addClass("inputerror").removeClass("inputok");
//    }else{
//        $("form[name='VinculacionIn26Form'] input[name='valConvIn']").addClass("inputok").removeClass("inputerror");
//    }
//    if($("form[name='VinculacionIn26Form'] input[name='valConvSupIn']").val() <= 0){
//         $("form[name='VinculacionIn26Form'] input[name='valConvSupIn']").addClass("inputerror").removeClass("inputok");
//    }else{
//        $("form[name='VinculacionIn26Form'] input[name='valConvSupIn']").addClass("inputok").removeClass("inputerror");
//    }
    
}
function indicador26(control){
    if (control.value == "")
        control.value = 0;
    if (!/^([0-9])*$/.test(control.value)){
        $().alerta("rojo","El valor '" + control.value + "' no es v&aacute;lido, debe ser un n&uacute;mero entero.");
        $(control).addClass("inputerror").removeClass("inputok");
        return;
    }else{
        $(control).val(Number(control.value));
        $(control).addClass("inputok").removeClass("inputerror");
        /*Modificar a partir de acá*/
        cargarDatos26();
    /*Hasta aqui!*/
    }
}

function cargarDatos26(){
    var totOrg = parseInt($("form[name='VinculacionIn26Form'] input[name='valPub']").val())+ parseInt($("form[name='VinculacionIn26Form'] input[name='valPri']").val())+ parseInt($("form[name='VinculacionIn26Form'] input[name='valSoc']").val());
    $("form[name='VinculacionIn26Form'] input[name='totOrgVinc']").val(totOrg);
    if($("form[name='VinculacionIn26Form'] input[name='totOrgVinc']").val() != 0){
        $("form[name='VinculacionIn26Form'] input[name='porcPub']").val(getDecimal( 
            parseInt($("form[name='VinculacionIn26Form'] input[name='valPub']").val()) / totOrg * 100 )+" %");
        $("form[name='VinculacionIn26Form'] input[name='porcPri']").val(getDecimal(
            parseInt($("form[name='VinculacionIn26Form'] input[name='valPri']").val()) / totOrg * 100 )+" %");
        $("form[name='VinculacionIn26Form'] input[name='porcSoc']").val(getDecimal(
            parseInt($("form[name='VinculacionIn26Form'] input[name='valSoc']").val()) / totOrg * 100 )+" %");
        $("form[name='VinculacionIn26Form'] input[name='porcSum']").val(getDecimal(
            parseFloat($("form[name='VinculacionIn26Form'] input[name='porcSoc']").val()) +
            parseFloat($("form[name='VinculacionIn26Form'] input[name='porcPri']").val()) +
            parseFloat($("form[name='VinculacionIn26Form'] input[name='porcPub']").val())
            )+ " %" );
    }else{
        $("form[name='VinculacionIn26Form'] input[name^='porc']").val(0);
    }
    
   cargarDatos262();
   cargarDatos263();
}


function cargarDatos262()
{
    var PubIn = parseInt($("form[name='VinculacionIn26Form'] input[name='valPubIn']").val());
    var PriIn = parseInt($("form[name='VinculacionIn26Form'] input[name='valPriIn']").val());
    var SocIn = parseInt($("form[name='VinculacionIn26Form'] input[name='valSocIn']").val());
    
    var totOrgVincIn = PubIn + PriIn + SocIn;
    $("form[name='VinculacionIn26Form'] input[name='totOrgVincIn']").val(totOrgVincIn);
    
    var porcPubIn = getDecimal( parseFloat(((PubIn / totOrgVincIn) * 100)) );
    var porcPriIn = getDecimal( parseFloat(((PriIn / totOrgVincIn) * 100)) );
    var porcSocIn = getDecimal( parseFloat(((SocIn / totOrgVincIn) * 100)) );
    if(isNaN(porcPubIn)){        porcPubIn=0;    }
    if(isNaN(porcPriIn)){        porcPriIn=0;    }
    if(isNaN(porcSocIn)){        porcSocIn=0;    }
    $("form[name='VinculacionIn26Form'] input[name='porcPubIn']").val(porcPubIn+" %");
    $("form[name='VinculacionIn26Form'] input[name='porcPriIn']").val(porcPriIn+" %");
    $("form[name='VinculacionIn26Form'] input[name='porcSocIn']").val(porcSocIn+" %");
    var porcSumin= getDecimal( (porcPubIn + porcPriIn +porcSocIn) );
    if(isNaN(porcSumin)){
        porcSumin="0"
    }
    $("form[name='VinculacionIn26Form'] input[name='porcSumIn']").val(porcSumin+" %");
    
    
}


function cargarDatos263()
{
    //Nacional
    var alumnonac = parseInt($("form[name='VinculacionIn26Form'] input[name='valAlumno']").val());
    var docentenac = parseInt($("form[name='VinculacionIn26Form'] input[name='valDocentes']").val());
    
    var nacional = alumnonac + docentenac;
    $("form[name='VinculacionIn26Form'] input[name='valTotalNacional']").val(nacional);

    var poralumnonac = getDecimal( parseFloat(((alumnonac / nacional) *100)) );
    var pordocentenac = getDecimal( parseFloat(((docentenac / nacional) *100)) );
     if(isNaN(poralumnonac)){        poralumnonac= 0;     }
     if(isNaN(pordocentenac)){        pordocentenac= 0;     }
    $("form[name='VinculacionIn26Form'] input[name='porcAlumno']").val(poralumnonac+" %");
    $("form[name='VinculacionIn26Form'] input[name='porcDocente']").val(pordocentenac+" %");
    $("form[name='VinculacionIn26Form'] input[name='porcTotalNacional']").val(poralumnonac + pordocentenac + " %");
    
    //Internacional
    var alumnoin = parseInt($("form[name='VinculacionIn26Form'] input[name='valAlumnoIn']").val());
    var docentein = parseInt($("form[name='VinculacionIn26Form'] input[name='valDocenteIn']").val());
    
    var internacional = alumnoin + docentein;
    $("form[name='VinculacionIn26Form'] input[name='valTotalInternacional']").val(internacional)

    var poralumnoin = getDecimal( parseFloat(((alumnoin / internacional) *100)) );
    var pordocentein = getDecimal( parseFloat(((docentein / internacional) *100)) );
    if(isNaN(poralumnoin)){        poralumnoin= 0;     }
     if(isNaN(pordocentein)){        pordocentein= 0;     }
    $("form[name='VinculacionIn26Form'] input[name='porcAlumnoIn']").val(poralumnoin+" %");
    $("form[name='VinculacionIn26Form'] input[name='porcDocenteIn']").val(pordocentein+" %");
    $("form[name='VinculacionIn26Form'] input[name='porcTotalInternacional']").val(poralumnoin + pordocentein + " %");
}