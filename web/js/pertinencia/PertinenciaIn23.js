


function calcularTa4(){
    
    var totalAlumnos= $("form[name='PertinenciaIn23Form'] input[name='total']").val();
    var totalPromep= $("form[name='PertinenciaIn23Form'] input[name='perfilptc5']").val();
    var p1=(parseFloat(totalPromep)/parseFloat(totalAlumnos))*100;
    p1= getDecimal(p1)
    if(isNaN(p1)){
        p1="0.0";
    }
    $("#porcentajeSINPEE").html("");
    $("#porcentajeSINPEE").html(p1+"%");
    
    var posgrado= $("form[name='PertinenciaIn23Form'] input[name='subtotal2']").val();
    
    var p2=(parseFloat(posgrado)/parseFloat(totalAlumnos))*100;
    p2= getDecimal(p2)
    if(isNaN(p2)){
        p2="0.0";
    }
    $("form[name='PertinenciaIn23Form'] input[name='totalPosgrado']").val("");
    $("form[name='PertinenciaIn23Form'] input[name='totalPosgrado']").val(p2+"%");
              
}

function indicador23(control){
    if (control.value==""){
        control.value=0;
        return;
    }
    if (!/^([0-9])*$/.test(control.value) || control.value==""){ //SI EL VALOR NO ES VÁLIDO
        $().alerta("rojo","El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;
    }else{
        $(control).removeClass("inputerror").addClass("inputok");
        control.value=Number(control.value);
        operacionesPrimarias23();
        operacionesSecundarias23();
    }    
}

function operacionesPrimarias23(){
    //SUBTOTAL1
    var errores=false;
    for (var i=1;i<=12;i++){
        if ($("form[name='PertinenciaIn23Form'] input[name='Estudios"+i+"']").hasClass("inputerror")){
            errores=true;
        }
    }

    if (errores==false){
        var total=0;
        for (i=1;i<=8;i++){
            total=parseInt(total)+parseInt($("form[name='PertinenciaIn23Form'] input[name='Estudios"+i+"']").val());
        }
        $("form[name='PertinenciaIn23Form'] input[name='subtotal1']").val(total);

        //SUBTOTAL2
        total=0;
        for (i=9;i<=12;i++){
            total=parseInt(total)+parseInt($("form[name='PertinenciaIn23Form'] input[name='Estudios"+i+"']").val());
        }
        $("form[name='PertinenciaIn23Form'] input[name='subtotal2']").val(total);
    
        //TOTAL
        var subtotal1=parseInt($("form[name='PertinenciaIn23Form'] input[name='subtotal1']").val());
        var subtotal2=parseInt($("form[name='PertinenciaIn23Form'] input[name='subtotal2']").val());
        $("form[name='PertinenciaIn23Form'] input[name='total']").val(subtotal1+subtotal2);
        var ptcp1 =(getDecimal(subtotal2/(subtotal1+subtotal2))*100 )
        $("form[name='PertinenciaIn23Form'] input[name='totalPosgrado']").val(ptcp1+" %");
    }

}

function operacionesSecundarias23(){
    for (var i=1;i<=7;i++){
        if (parseInt($("form[name='PertinenciaIn23Form'] input[name='perfilptc"+i+"']").val())>
            parseInt($("form[name='PertinenciaIn23Form'] input[name='total']").val())){
            $("form[name='PertinenciaIn23Form'] input[name='perfilptc"+i+"']").globo("enable")
            .globo("update", "No puede ser m&aacute;s<br/>grande que el total<br/>de PTC")
            .removeClass("inputok").addClass("inputerror");
        }else{
            $("form[name='PertinenciaIn23Form'] input[name='perfilptc"+i+"']").globo("disable")
            .removeClass("inputerror").addClass("inputok");
        }
    }
}

function indicador23segundo(control){
    if (control.value==""){
        control.value=0;
        return;
    }
    if (!/^([0-9])*$/.test(control.value) || control.value==""){ //SI EL VALOR NO ES VÁLIDO
        $().alerta("rojo","El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;
    }else{
        control.value=Number(control.value);
        if (parseInt($("form[name='PertinenciaIn23Form'] input[name='"+control.name+"']").val())>
            parseInt($("form[name='PertinenciaIn23Form'] input[name='total']").val())){
            $("form[name='PertinenciaIn23Form'] input[name='"+control.name+"']").globo("enable")
            .globo("update", "No puede ser m&aacute;s<br/>grande que el total<br/>de PTC")
            .removeClass("inputok").addClass("inputerror");
            operacionesPrimarias23();
        }else{
            $("form[name='PertinenciaIn23Form'] input[name='"+control.name+"']").globo("disable")
            .removeClass("inputerror").addClass("inputok");
        }
    }
    calcularTa4();
}

function cuerposAcademicos23(control){
    var total=0;
    if (control.value==""){
        control.value=0;
        return;
    }
    if (!/^([0-9])*$/.test(control.value) || control.value==""){ //SI EL VALOR NO ES VÁLIDO
        $().alerta("rojo","El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;
    }else{
        $(control).removeClass("inputerror").addClass("inputok");
        if (parseInt($("form[name='PertinenciaIn23Form'] input[name='"+control.name+"']").val())>
            parseInt($("form[name='PertinenciaIn23Form'] input[name='total']").val())){
            $("form[name='PertinenciaIn23Form'] input[name='"+control.name+"']").globo("enable")
            .globo("update", "No puede ser m&aacute;s<br/>grande que el total<br/>de PTC")
            .removeClass("inputok").addClass("inputerror").globo("show");
        }else{
            $("form[name='PertinenciaIn23Form'] input[name='"+control.name+"']").globo("disable")
            .removeClass("inputerror").addClass("inputok");
        }
        var errores=false;
        //PARA SABER SI EXISTEN ERRRORES
        for (var i=1;i<=6;i++){
            if ($("form[name='PertinenciaIn23Form'] input[name='cuerposAcademicos"+i+"']").hasClass("inputerror")){
                errores=true;
            }
        }
        //        //SINO EXISTEN ERRORES ENTONCES...
        if (errores==false){
            for (i=1; i<=3;i++){//REALIZA SUMA
                total=parseInt(total)+parseInt($("form[name='PertinenciaIn23Form'] input[name='cuerposAcademicos"+i+"']").val());
            }
            $("form[name='PertinenciaIn23Form'] input[name='totalCuerpos']").val(total);
            if (parseInt($("form[name='PertinenciaIn23Form'] input[name='totalCuerpos']").val())>
                parseInt($("form[name='PertinenciaIn23Form'] input[name='total']").val())){
                $("form[name='PertinenciaIn23Form'] input[name='totalCuerpos']").globo("enable")
                .globo("update", "No puede ser m&aacute;s<br/>grande que el total<br/>de PTC").globo("show");
            }else{
                $("form[name='PertinenciaIn23Form'] input[name='totalCuerpos']").globo("disable");
            }
        }
    }
}
//
function guardarIndicador23(){
    var cadena="Error";
    if (!$("form[name='PertinenciaIn23Form'] input").hasClass("inputerror")){
        if (parseInt($("form[name='PertinenciaIn23Form'] input[name='total']").val())
            != parseInt($("form[name='PertinenciaIn23Form'] input[name='ptc_base']").val())){
            $().alerta('amarillo', 'Datos NO guardados. La suma del cuadro 23.1 debe coincidir con el total de PTC de la Universidad ("'+$("form[name='PertinenciaIn23Form'] input[name='ptc_base']").val()+'")');
          return "Error";
        }
        if (parseInt($("form[name='PertinenciaIn23Form'] input[name='totalCuerpos']").val())
            <=parseInt($("form[name='PertinenciaIn23Form'] input[name='total']").val())){
             cadena="";
            for (var i=1;i<=12;i++){
                cadena=cadena.concat($("form[name='PertinenciaIn23Form'] input[name='Estudios"+i+"']").val(),",");
            }
            for (i=1;i<=7;i++){
                cadena=cadena.concat($("form[name='PertinenciaIn23Form'] input[name='perfilptc"+i+"']").val(),",");
            }
            for (i=1;i<=3;i++){
                cadena=cadena.concat($("form[name='PertinenciaIn23Form'] input[name='cuerposAcademicos"+i+"']").val(),",");
            }
            //$().alerta('verde', 'Datos guardados correctamente.');
               //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='PertinenciaIn23Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "23";
                GuardarComentarios(comentario, indicador);
            }
        }else{
            //$().alerta('rojo', 'Datos NO guardados. Verifiquelos Por favor');
            $("form[name='PertinenciaIn23Form'] input[name='totalCuerpos']").globo("show");
            //agregada
            cadena="Error";
            //return -1;
        }
    }else{
        //$().alerta('rojo', 'Datos NO guardados. Verifiquelos Por favor');
        cadena="Error";
    }
    
    return cadena;
}

function operacionesIndicador23(){
    //SUMAS-----------------------------------------------------------------------------------------------------------
    var total=0;
    
    
    if ($("form[name='PertinenciaIn23Form'] input").hasClass("inputerror")){
        $("form[name='PertinenciaIn23Form'] input[name='totalCuerpos']").val(0)
        $("form[name='PertinenciaIn23Form'] input[name='subtotal1']").val(0);
        $("form[name='PertinenciaIn23Form'] input[name='subtotal2']").val(0);
        $("form[name='PertinenciaIn23Form'] input[name='total']").val(0);
        
        
        
    }else{    
        
        for (var i=1;i<=8;i++){
            total=parseInt(total)+parseInt($("form[name='PertinenciaIn23Form'] input[name='Estudios"+i+"']").val());
        }
        $("form[name='PertinenciaIn23Form'] input[name='subtotal1']").val(total);

        //    //SUBTOTAL2
        total=0;
        for (i=9;i<=12;i++){
            total=parseInt(total)+parseInt($("form[name='PertinenciaIn23Form'] input[name='Estudios"+i+"']").val());
        }
        $("form[name='PertinenciaIn23Form'] input[name='subtotal2']").val(total);
        //
        //    //TOTAL
        var subtotal1=parseInt($("form[name='PertinenciaIn23Form'] input[name='subtotal1']").val());
        var subtotal2=parseInt($("form[name='PertinenciaIn23Form'] input[name='subtotal2']").val());
        $("form[name='PertinenciaIn23Form'] input[name='total']").val(subtotal1+subtotal2);
        //    
        //    //SUMA DOS ----------------------------------------------------------------------------------
        total=0;
        for (i=1; i<=3;i++){//REALIZA SUMA
            total=parseInt(total)+parseInt($("form[name='PertinenciaIn23Form'] input[name='cuerposAcademicos"+i+"']").val());
        }
        $("form[name='PertinenciaIn23Form'] input[name='totalCuerpos']").val(total); 
    }
    
    calcularTa4();
}