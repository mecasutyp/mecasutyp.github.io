//* @author Daniel Ramìrez Torres
//Actualización 2016: Salvador Zamora Arias

function noControles6(){
    var i = 0;
    while(document.forms['EficaciaIn6Form'][i].name != "fin"){
        i++;
    }
    return i;
}
function indicador6(control){
    if (control.value == ""){
        control.value = 0;
    }
    if (!/^([0-9])*$/.test(control.value)){
        $().alerta("amarillo", "El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        nulosEficacia6();
        return;
    }else {
        obtenervalores6();
    }
    
    var num_niveles=$("form[name='EficaciaIn6Form'] input[name='niveles']").val();
    for(var i=0; i< num_niveles;i++){
       var  e_trabajando = parseInt($("form[name='EficaciaIn6Form'] input[name='e_trabajando" + i + "']").val());            
       $("form[name='EficaciaIn6Form'] input[name='e_trabajando" + i + "']").val(e_trabajando);
        var  afin = parseInt($("form[name='EficaciaIn6Form'] input[name='afin" + i + "']").val());            
       $("form[name='EficaciaIn6Form'] input[name='afin" + i + "']").val(afin);
       var  totalegresados = $("form[name='EficaciaIn6Form'] input[name='totalegresados" + i + "']").val();
        
        if(e_trabajando > totalegresados){
            $().alerta("rojo","El valor Egresados trabajando a seis meses de egreso no puede ser mayor al total de egresados");
             $("form[name='EficaciaIn6Form'] input[name='e_trabajando"+i+"']").removeClass("inputok").addClass("inputerror");
        }else{
              $("form[name='EficaciaIn6Form'] input[name='e_trabajando"+i+"']").removeClass("inputerror").addClass("inputok");
        }
        if(afin > e_trabajando){
            $().alerta("rojo","El valor Egresados cuya actividad laboral es acorde a su formaci&oacute;n acad&eacute;mica no puede ser mayor a egresados trabajando a seis meses de egreso");
             $("form[name='EficaciaIn6Form'] input[name='afin"+i+"']").removeClass("inputok").addClass("inputerror");
        }else{
              $("form[name='EficaciaIn6Form'] input[name='afin"+i+"']").removeClass("inputerror").addClass("inputok");
        }
    }
    
}

function obtenervalores6(){
    var e_trabajando = 0;
    var totalegresados = 0;
    var afin = 0;
    var esp = 0;
    var ess = 0;
    var est = 0;
    var cadena = "";
    // El valor  del for es saber cuantos programas educativos estan configurados se cargan en este indicador. 
    for(i = 0; i < $("form[name='EficaciaIn6Form'] input[name='nivelesuniversidad']").val(); i++){
        //Programa educativo
        cadena =  cadena.concat($("form[name='EficaciaIn6Form'] input[name='programaeducativo" + i + "']").val(),",");
        //Indicador 6.1
        e_trabajando = $("form[name='EficaciaIn6Form'] input[name='e_trabajando" + i + "']").val();            
        totalegresados = $("form[name='EficaciaIn6Form'] input[name='totalegresados" + i + "']").val();
        afin = $("form[name='EficaciaIn6Form'] input[name='afin" + i + "']").val();
        
        //Primeros datos de la tabla eficaciain6
        cadena = cadena.concat( e_trabajando, ",")
        cadena = cadena.concat( afin, ",")
        
        $("form[name='EficaciaIn6Form'] input[name='eml" + i + "']").val(getDecimal(parseInt(e_trabajando) / parseInt(totalegresados) * 100) + " %")
        $("form[name='EficaciaIn6Form'] input[name='eta" + i + "']").val(getDecimal(parseInt(afin) / parseInt(e_trabajando) * 100) + " %")
        
        //Indicador 6.2
        esp = $("form[name='EficaciaIn6Form'] input[name='esp" + i + "']").val();
        ess = $("form[name='EficaciaIn6Form'] input[name='ess" + i + "']").val();
        est = $("form[name='EficaciaIn6Form'] input[name='est" + i + "']").val();
        
        //Segundos datos de la tabla eficaciain6
        cadena = cadena.concat( esp, ",")
        cadena = cadena.concat( ess, ",")
        cadena = cadena.concat( est, "-")
        
        
        var total = parseInt(esp)+parseInt(ess)+parseInt(est);
        $("form[name='EficaciaIn6Form'] input[name='tegresadossector" + i + "']").val(total)
        
        var totemlsp = getDecimal((parseInt(esp) / parseInt(total))*100);
        var totemlss = getDecimal((parseInt(ess) / parseInt(total))*100);
        var totemlst = getDecimal((parseInt(est) / parseInt(total))*100);
      if(!isNaN(totemlsp)){    
        $("form[name='EficaciaIn6Form'] input[name='totemlsp" + i + "']").val(totemlsp + " %")
        $("form[name='EficaciaIn6Form'] input[name='totemlss" + i + "']").val(totemlss + " %")
        $("form[name='EficaciaIn6Form'] input[name='totemlst" + i + "']").val(totemlst + " %")
           
        $("form[name='EficaciaIn6Form'] input[name='totalsector" + i + "']").val(parseFloat(totemlsp)+parseFloat(totemlss)+parseFloat(totemlst))
    }     
    
////        $("form[name='EficaciaIn6Form'] input[name='totaleconomica" + i + "']").val(getDecimal(
////            parseFloat(totemleag) + parseFloat(totemlmin) + parseFloat(totemlatc) + 
////            parseFloat(totemlaco) + parseFloat(totemlsfs) + parseFloat(totemlaim) + 
////            parseFloat(totemlapa) + parseFloat(totemlasp) + parseFloat(totemlacm)))           
//    }   
}
    
    return cadena;
}

function nulosEficacia6(){
    if ($("form[name='EficaciaIn6Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        for(i = 0; i < noControles6("EficaciaIn6Form"); i++){
            document.forms["EficaciaIn6Form"][i+3].value=0;
            document.forms["EficaciaIn6Form"][i+4].value=0;
            i+=4;
        }
    }
}

function guardarEficaciaIn6(){
    var x="";
    if ($("form[name='EficaciaIn6Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        //$().alerta('rojo', 'Error al guardar. Por favor verifique los datos.');
        x="Error";
    }else{
        //SE GUARDARA LOS COMENTARIOS
        var comentario = $("form[name='EficaciaIn6Form'] input[name='comentario']").val();
         if(comentario != "Sin comentarios"){
             var indicador = "6";
             GuardarComentarios(comentario, indicador);
         }
        x = obtenervalores6();
        //$().alerta('verde', 'Datos guardados correctamente.');
    }
    return x;
}



