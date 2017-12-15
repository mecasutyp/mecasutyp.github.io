function indicador20(control,ini,fin,numSum){
    if (!/^([0-9])*$/.test(control.value)){ //SI EL VALOR NO ES VÁLIDO
        $().alerta("rojo","El valor '"+ control.value + "' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        if ($('form[name="PertinenciaIn20Form"] input').hasClass("inputerror")){
            $('li[class*="ui-tabs-selected ui-state-active"]').globo("update", "<center>Indicador con <br/> errores, favor <br/> de revisar.</center>").globo("show");
        }else{
            $('li[class*="ui-tabs-selected ui-state-active"]').globo("hide");
        }
        return;
    }else{
        control.value=Number(control.value);
        $(control).removeClass("inputerror").addClass("inputok");
        if(!$("form[name='PertinenciaIn20Form'] input").hasClass("inputerror")){
            if(ini!=0){
                operacionesPertinencia20(ini,fin,numSum);
            }
        }
    }
}

function checkedToBool(control){
    if($("form[name='PertinenciaIn20Form'] input[name='val_"+control+"']").is(':checked')) {  
        if(control==22){
            $("form[name='PertinenciaIn20Form'] input[name='val_23']").removeAttr("disabled");  
        }
        return 1;
    }else{
        if(control==22){
            $("form[name='PertinenciaIn20Form'] input[name='val_23']").attr("disabled","disabled");
            $("form[name='PertinenciaIn20Form'] input[name='val_23']").val("");
        }
        return 0;
    }
}
 
 
function guardarIndicador20(){
    var cadena="Error";
    operacionesPertinencia201(24,27,6);
    operacionesPertinencia201(28,31,7);
    if ($("form[name='PertinenciaIn20Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        // $().alerta("rojo","Error al guardar. Por favor verifique los datos");
        return cadena;
    }
    if(parseInt($("form[name='PertinenciaIn20Form'] input[name='total6']").val()) != parseInt($("form[name='PertinenciaIn20Form'] input[name='totPtc']").val())){
        $().alerta("amarillo","Cuidado!, el total de PTC del cuadro 20.6 no coincide con los PTC totales de la universidad ('"+$("form[name='PertinenciaIn20Form'] input[name='totPtc']").val()+"')");
        return cadena;
    }
    
    if(parseInt($("form[name='PertinenciaIn20Form'] input[name='val_27']").val()) > parseInt($("form[name='PertinenciaIn20Form'] input[name='totPtc']").val())){
        $().alerta("amarillo","Cuidado!, el total de PTC que est&aacute;n aplicando estos enfoques no puede ser mayor al PTC totales de la universidad ('"+$("form[name='PertinenciaIn20Form'] input[name='totPtc']").val()+"') del cuadro 20.6");
        return cadena;
    }
    
    if($("form[name='PertinenciaIn20Form'] input[name='total7']").val() != $("form[name='PertinenciaIn20Form'] input[name='totAsi']").val()){
        $().alerta("amarillo","Cuidado!, el total de PA del cuadro 20.7 no coincide con los PA totales de la universidad ('"+$("form[name='PertinenciaIn20Form'] input[name='totAsi']").val()+"')");
        return cadena;
    }else if(parseInt($("form[name='PertinenciaIn20Form'] input[name='val_31']").val()) > parseInt($("form[name='PertinenciaIn20Form'] input[name='total7']").val())){
        $().alerta("amarillo","Cuidado!, el total de PA que están aplicando estos enfoques no puede ser mayor al PA totales de la universidad ('"+$("form[name='PertinenciaIn20Form'] input[name='totAsi']").val()+"') del cuadro 20.7");
        return cadena;
    }else{
        cadena="-";
        for(var i=1;i<32;i++){
            if(i==19 || i==20 || i==21 || i==22){
                cadena+=checkedToBool(i)+",";
            }
            else if(i==23){
                cadena += replaceAll($("form[name='PertinenciaIn20Form'] input[name='val_"+i+"']").val(),",","");
                cadena+=",";
            }
            else{
                cadena+=$("form[name='PertinenciaIn20Form'] input[name='val_"+i+"']").val()+",";
                
            }     
        }
        cadena+="-";
           //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='PertinenciaIn20Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "20";
                GuardarComentarios(comentario, indicador);
            }
    //retrieveURL('/PertinenciaIn20.msut?valores='+cadena, 'PertinenciaIn20Form');
    //$().alerta("verde","Datos Guardados Correctamente.");
    }
    return cadena;
}
function operacionesPertinencia201(ini,fin,numSum){
    if (!$("form[name='PertinenciaIn20Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
        operacionesPertinencia20(ini,fin,numSum);
    }  
}

function operacionesPertinencia20(ini,fin,numSum){
    var suma=0;
    for(var i=ini;i<fin;i++){
        if(i<fin){
            suma+=parseInt($("form[name='PertinenciaIn20Form'] input[name='val_"+i+"']").val());
        }
    }
    $("form[name='PertinenciaIn20Form'] input[name='total"+numSum+"']").val(suma);
}
 