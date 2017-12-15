//* @author Daniel Ramìrez Torres
//Actualización 2016: Salvador Zamora Arias
function indicador9(control){
    if (control.value == ""){
        control.value = 0;
    }
    if (!/^([0-9])*$/.test(control.value)){
        $().alerta("amarillo", "El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;
    }else{
        $(control).addClass("inputok").removeClass("inputerror");
        $(control).val(Number(control.value));
        operacionesEficaciaIn9();
    }
    if ($('form[name="EficaciaIn9Form"] input').hasClass("inputerror")){
        $('li[class*="ui-tabs-selected ui-state-active"]').globo("update", "<center>Indicador con <br/> errores, favor <br/> de revisar.</center>").globo("show");
    }else{
        $('li[class*="ui-tabs-selected ui-state-active"]').globo("hide");
    }
}

function guardarEficaciaIn9(){
    var x="";
    if ($("form[name='EficaciaIn9Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        x="Error";
        //$().alerta('rojo', 'Error al guardar. Por favor verifique los datos.');
    }else{
        //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='EficaciaIn9Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "9";
                GuardarComentarios(comentario, indicador);
            }
        x = operacionesEficaciaIn9();
        //$().alerta('verde', 'Datos guardados correctamente.');
    }
    return x;
}

function operacionesEficaciaIn9(){
    var cadena="x";
    var noCuadros=$("form[name='EficaciaIn9Form'] input[name='noCuadros']").val();

    for(var l=1;l<=noCuadros;l++){
        var egre_continuan=  $("form[name='EficaciaIn9Form'] input[name='egre_continuan"+l+"']").val();
        var total_egresados= $("form[name='EficaciaIn9Form'] input[name='total_egresados"+l+"']").val();
        var egre_continuan_ut=$("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut"+l+"']").val();
        var egre_continuan_ut_con=$("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut_con"+l+"']").val();      
       
        if(total_egresados==0 || egre_continuan==0){
            $("form[name='EficaciaIn9Form'] input[name='sup1"+l+"']").val(0 + " %");
        }else{
            $("form[name='EficaciaIn9Form'] input[name='sup1"+l+"']").val(getDecimal(parseFloat(parseFloat(egre_continuan)/parseFloat(total_egresados)*100)) + " %");
        }
        if(egre_continuan_ut_con==0 || egre_continuan_ut==0){
            $("form[name='EficaciaIn9Form'] input[name='sup"+l+"']").val(0 + " %");
        }
        else{
            $("form[name='EficaciaIn9Form'] input[name='sup"+l+"']").val(getDecimal(parseFloat(parseFloat(egre_continuan_ut)/parseFloat(egre_continuan_ut_con)*100)) + " %");
        }
        if(parseInt(egre_continuan)>parseInt(total_egresados)){
            $("form[name='EficaciaIn9Form'] input[name='egre_continuan"+l+"']").globo("enable").globo("update", "<center>No puede ser mayor al n&uacute;mero<br/> total de egresados</center>").removeClass("inputok").addClass("inputerror");
        }else{
            $("form[name='EficaciaIn9Form'] input[name='egre_continuan"+l+"']").globo("disable");
        }
        //arriba
        if($("form[name='EficaciaIn9Form'] input[name='id"+l+"']").val()!=1){
            $("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut_con"+l+"']").val(total_egresados);
            if(parseInt(egre_continuan_ut)>parseInt(egre_continuan_ut_con)){
                $("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut"+l+"']").globo("enable").globo("update", "<center>No puede ser mayor al n&uacute;mero<br/> total de egresados</center>").removeClass("inputok").addClass("inputerror");
            }else{
                $("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut"+l+"']").globo("disable");
            }
        }
        if($("form[name='EficaciaIn9Form'] input[name='id"+l+"']").val()==1){
            $("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut_es"+l+"']").val(parseInt(egre_continuan_ut)+parseInt(egre_continuan_ut_con));
            if(parseInt($("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut_es"+l+"']").val())>parseInt(total_egresados)){
                $().alerta('amarillo', 'El total de egresados que continuan estudios superiores de licencia profesional o licenciatura en una universidad no puede ser mayor al total de egresados ("'+total_egresados+'")');
                $("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut"+l+"']").removeClass("inputok").addClass("inputerror");
                $("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut_con"+l+"']").removeClass("inputok").addClass("inputerror");
            }
            if(parseInt(total_egresados)>=parseInt($("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut_es"+l+"']").val())){
                $("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut"+l+"']").removeClass("inputerror");
                $("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut_con"+l+"']").removeClass("inputerror");
            }
            $("form[name='EficaciaIn9Form'] input[name='egre_continuan_tsu"+l+"']").val(total_egresados);
            $("form[name='EficaciaIn9Form'] input[name='sup2"+l+"']").val(getDecimal(parseFloat(parseFloat(egre_continuan_ut)/parseFloat(total_egresados)*100)) + " %");
            $("form[name='EficaciaIn9Form'] input[name='sup3"+l+"']").val(getDecimal(parseFloat(parseFloat(egre_continuan_ut_con)/parseFloat(total_egresados)*100)) + " %");
            $("form[name='EficaciaIn9Form'] input[name='sup4"+l+"']").val(getDecimal(parseFloat(parseFloat($("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut_es"+l+"']").val())/parseFloat(total_egresados)*100)) + " %");
            if ($("form[name='EficaciaIn9Form'] input").hasClass("inputerror") || $("form[name='EficaciaIn9Form'] input[name='total_egresados"+l+"']").val()==0){
                $("form[name='EficaciaIn9Form'] input[name='sup2"+l+"']").val(0 + " %");
                $("form[name='EficaciaIn9Form'] input[name='sup3"+l+"']").val(0 + " %");
                $("form[name='EficaciaIn9Form'] input[name='sup4"+l+"']").val(0 + " %");
                $("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut_es"+l+"']").val(0);
            }
        }
        cadena=cadena.concat($("form[name='EficaciaIn9Form'] input[name='id"+l+"']").val(),",",$("form[name='EficaciaIn9Form'] input[name='egre_continuan"+l+"']").val(),",",$("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut"+l+"']").val(),",",$("form[name='EficaciaIn9Form'] input[name='egre_continuan_ut_con"+l+"']").val());
        if ($("form[name='EficaciaIn9Form'] input").hasClass("inputerror")){
            $("form[name='EficaciaIn9Form'] input[name='sup"+l+"']").val(0 + " %");
            $("form[name='EficaciaIn9Form'] input[name='sup1"+l+"']").val(0 + " %");
        }
        cadena =cadena.concat("x");
    }
    return(cadena);
}