//DEVUELVE EL NÚMERO DE CONTROLES EN EL DOCUMENTO
//Daniel Ramírez Torres

function ind31aplica(){
    var value = $("[name='radioAplica']:checked").val();
    if (value==="1"){
        $("#vinc31").slideDown();
    }else{ //no aplica
        $("#vinc31").slideUp();
        $("#vinc31 input[type='text']").val(0);
        $("#vinc31 input[class='inputok'][name^='val_']").removeClass("inputok");
    }
}

function indicador31(control,cu,fi,save,dofi){
    if (control.value==""){
        control.value=0;
    }
    if (!/^([0-9])*$/.test(control.value)){ //SI EL VALOR NO ES VÁLIDO
        $().alerta("rojo","El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;
    }else{
        $(control).removeClass("inputerror").addClass("inputok");
        control.value=Number(control.value);
        if (!$("form[name='VinculacionIn31Form'] input").hasClass("inputerror")){            
            operacionesVinculacion31(cu,fi,save,dofi);
        }
    }
}

function operacionesVinculacion31(cu,fi,save,dofi){
    if(parseInt(save)==0){
        var col_A = 0, col_B = 0, col_C = 0, col_D = 0, col_E = 0, col_F = 0, col_G = 0, col_H = 0, col_I = 0, col_J = 0, col_K5 = 0, col_K10 = 0, num_Preguntas=0;
        for(var i=1; i<$("form[name='VinculacionIn31Form'] input[name='no_fil_niv,"+cu+"']").val(); i++){             
            if(parseInt(dofi)==0){               
                $("form[name='VinculacionIn31Form'] input[name='tot_H,"+cu+","+i+"']").val(parseInt($("form[name='VinculacionIn31Form'] input[name='val_A,"+cu+","+i+"']").val())+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_B,"+cu+","+i+"']").val())+ parseInt($("form[name='VinculacionIn31Form'] input[name='val_C,"+cu+","+i+"']").val())+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_D,"+cu+","+i+"']").val())+ parseInt($("form[name='VinculacionIn31Form'] input[name='val_E,"+cu+","+i+"']").val())+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_F,"+cu+","+i+"']").val())+ parseInt($("form[name='VinculacionIn31Form'] input[name='val_G,"+cu+","+i+"']").val()));
        
                $("form[name='VinculacionIn31Form'] input[name='tot_I,"+cu+","+i+"']").val(parseInt($("form[name='VinculacionIn31Form'] input[name='val_A,"+cu+","+i+"']").val())+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_B,"+cu+","+i+"']").val())+ parseInt($("form[name='VinculacionIn31Form'] input[name='val_C,"+cu+","+i+"']").val())+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_D,"+cu+","+i+"']").val())+ parseInt($("form[name='VinculacionIn31Form'] input[name='val_E,"+cu+","+i+"']").val()));
        
                $("form[name='VinculacionIn31Form'] input[name='tot_J,"+cu+","+i+"']").val(parseInt($("form[name='VinculacionIn31Form'] input[name='val_A,"+cu+","+i+"']").val())*5+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_B,"+cu+","+i+"']").val()) * 4 + parseInt($("form[name='VinculacionIn31Form'] input[name='val_C,"+cu+","+i+"']").val())*3+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_D,"+cu+","+i+"']").val()) * 2 + parseInt($("form[name='VinculacionIn31Form'] input[name='val_E,"+cu+","+i+"']").val())*1);
            
                if(parseInt($("form[name='VinculacionIn31Form'] input[name='tot_I,"+cu+","+i+"']").val())==0){               
                } else{
                    $("form[name='VinculacionIn31Form'] input[name='tot_K_5,"+cu+","+i+"']").val(getDecimal((parseInt($("form[name='VinculacionIn31Form'] input[name='tot_J,"+cu+","+i+"']").val())/parseInt($("form[name='VinculacionIn31Form'] input[name='tot_I,"+cu+","+i+"']").val()))));
                }       
                $("form[name='VinculacionIn31Form'] input[name='tot_K_10,"+cu+","+i+"']").val(getDecimal((parseFloat($("form[name='VinculacionIn31Form'] input[name='tot_K_5,"+cu+","+i+"']").val())*2)));      
            }
            if(parseInt(dofi)==1){
                $("form[name='VinculacionIn31Form'] input[name='tot_H,"+cu+","+fi+"']").val(parseInt($("form[name='VinculacionIn31Form'] input[name='val_A,"+cu+","+fi+"']").val())+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_B,"+cu+","+fi+"']").val())+ parseInt($("form[name='VinculacionIn31Form'] input[name='val_C,"+cu+","+fi+"']").val())+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_D,"+cu+","+fi+"']").val())+ parseInt($("form[name='VinculacionIn31Form'] input[name='val_E,"+cu+","+fi+"']").val())+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_F,"+cu+","+fi+"']").val())+ parseInt($("form[name='VinculacionIn31Form'] input[name='val_G,"+cu+","+fi+"']").val()));
        
                $("form[name='VinculacionIn31Form'] input[name='tot_I,"+cu+","+fi+"']").val(parseInt($("form[name='VinculacionIn31Form'] input[name='val_A,"+cu+","+fi+"']").val())+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_B,"+cu+","+fi+"']").val())+ parseInt($("form[name='VinculacionIn31Form'] input[name='val_C,"+cu+","+fi+"']").val())+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_D,"+cu+","+fi+"']").val())+ parseInt($("form[name='VinculacionIn31Form'] input[name='val_E,"+cu+","+fi+"']").val()));
        
                $("form[name='VinculacionIn31Form'] input[name='tot_J,"+cu+","+fi+"']").val(parseInt($("form[name='VinculacionIn31Form'] input[name='val_A,"+cu+","+fi+"']").val())*5+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_B,"+cu+","+fi+"']").val()) * 4 + parseInt($("form[name='VinculacionIn31Form'] input[name='val_C,"+cu+","+fi+"']").val())*3+
                    parseInt($("form[name='VinculacionIn31Form'] input[name='val_D,"+cu+","+fi+"']").val()) * 2 + parseInt($("form[name='VinculacionIn31Form'] input[name='val_E,"+cu+","+fi+"']").val())*1);
            
                if(parseInt($("form[name='VinculacionIn31Form'] input[name='tot_I,"+cu+","+fi+"']").val())==0){                  
                    $("form[name='VinculacionIn31Form'] input[name='tot_K_5,"+cu+","+fi+"']").val(0);              
                } else{
                    $("form[name='VinculacionIn31Form'] input[name='tot_K_5,"+cu+","+fi+"']").val(getDecimal((parseInt($("form[name='VinculacionIn31Form'] input[name='tot_J,"+cu+","+fi+"']").val())/parseInt($("form[name='VinculacionIn31Form'] input[name='tot_I,"+cu+","+fi+"']").val()))));
                }       
                $("form[name='VinculacionIn31Form'] input[name='tot_K_10,"+cu+","+fi+"']").val(getDecimal((parseFloat($("form[name='VinculacionIn31Form'] input[name='tot_K_5,"+cu+","+fi+"']").val())*2)));      
            }     
            col_A+=parseInt($("form[name='VinculacionIn31Form'] input[name='val_A,"+cu+","+i+"']").val()),col_B+=parseInt($("form[name='VinculacionIn31Form'] input[name='val_B,"+cu+","+i+"']").val());
            col_C+=parseInt($("form[name='VinculacionIn31Form'] input[name='val_C,"+cu+","+i+"']").val()),col_D+=parseInt($("form[name='VinculacionIn31Form'] input[name='val_D,"+cu+","+i+"']").val());
            col_E+=parseInt($("form[name='VinculacionIn31Form'] input[name='val_E,"+cu+","+i+"']").val()),col_F+=parseInt($("form[name='VinculacionIn31Form'] input[name='val_F,"+cu+","+i+"']").val());
            col_G+=parseInt($("form[name='VinculacionIn31Form'] input[name='val_G,"+cu+","+i+"']").val()),col_H+=parseInt($("form[name='VinculacionIn31Form'] input[name='tot_H,"+cu+","+i+"']").val());
            col_I+=parseInt($("form[name='VinculacionIn31Form'] input[name='tot_I,"+cu+","+i+"']").val()),col_J+=parseInt($("form[name='VinculacionIn31Form'] input[name='tot_J,"+cu+","+i+"']").val());
            col_K5+=parseFloat($("form[name='VinculacionIn31Form'] input[name='tot_K_5,"+cu+","+i+"']").val()),col_K10+=parseFloat($("form[name='VinculacionIn31Form'] input[name='tot_K_10,"+cu+","+i+"']").val());              
        }        
        $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-1']").val(col_A),$("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-2']").val(col_B),
        $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-3']").val(col_C),$("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-4']").val(col_D),
        $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-5']").val(col_E),$("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-6']").val(col_F),
        $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-7']").val(col_G),$("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-8']").val(col_H);
        $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-9']").val(col_I),$("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-10']").val(col_J);
        $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-11']").val(getDecimal((col_J/col_I))),$("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-12']").val(getDecimal((parseFloat($("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-11']").val())*2)));             
        
    }
    
       num_Preguntas= parseInt($("form[name='VinculacionIn31Form'] input[name='totalpreguntas']").val());
        
     //DISTRIBUCION PORCENTUAL
        var col_A_D=0, col_B_D=0, col_C_D=0, col_D_D=0, col_E_D=0, col_F_D=0, col_G_D=0, col_I_D=0.0, col_H_TES=0;
        col_A_D=(getDecimal(col_A/col_H*100));
        col_B_D=(getDecimal(col_B/col_H*100));
        col_C_D=(getDecimal(col_C/col_H*100));
        col_D_D=(getDecimal(col_D/col_H*100));
        col_E_D=(getDecimal(col_E/col_H*100));
        col_F_D=(getDecimal(col_F/col_H*100));
        col_G_D=(getDecimal(col_G/col_H*100));
        if(isNaN(col_A_D)){ col_A_D= "0 %"; }else{col_A_D= col_A_D+" %";}//SI EL RESULTADO ES NAN
        if(isNaN(col_B_D)){ col_B_D= "0 %"; }else{col_B_D= col_B_D+" %";}
        if(isNaN(col_C_D)){ col_C_D= "0 %"; }else{col_C_D= col_C_D+" %";}
        if(isNaN(col_D_D)){ col_D_D= "0 %"; }else{col_D_D= col_D_D+" %";}
        if(isNaN(col_E_D)){ col_E_D= "0 %"; }else{col_E_D= col_E_D+" %";}
        if(isNaN(col_F_D)){ col_F_D= "0 %"; }else{col_F_D= col_F_D+" %";}
        if(isNaN(col_G_D)){ col_G_D= "0 %"; }else{col_G_D= col_G_D+" %";}
        col_H_TES=parseInt($("form[name='VinculacionIn31Form'] input[name='tot_H,"+cu+","+num_Preguntas+"']").val());
        col_I_D=( getDecimal(((col_A+col_B) / num_Preguntas)/ col_H_TES*100) );
        
        if(col_I_D == Number.POSITIVE_INFINITY || col_I_D == Number.NEGATIVE_INFINITY){//ES RESULTADO INFINITO
            col_I_D="";
        }else{
            if(isNaN(col_I_D)){ 
                col_I_D= ""; 
            }else{
                col_I_D= col_I_D+" %";
            }
        }
        $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-1-d-ms']").val(col_A_D), $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-2-d-s']").val(col_B_D),
        $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-3-d-rs']").val(col_C_D), $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-4-d-ps']").val(col_D_D),
        $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-5-d-ns']").val(col_E_D), $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-6-d-na']").val(col_F_D),
        $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-7-d-ne']").val(col_G_D), $("form[name='VinculacionIn31Form'] input[name='tv_"+cu+"-9-d-tes']").val(col_I_D);
         col_A=0,col_B=0,col_C=0,col_D=0,col_E=0,col_F=0,col_G=0,col_H=0,col_I=0,col_J=0,col_K5=0,col_K10=0;
    if(parseInt(save)==1){ 
        var cadena="-";
        for(var k=1;k<cu;k++){
            for(var l=1;l<$("form[name='VinculacionIn31Form'] input[name='no_fil_niv,"+k+"']").val();l++){
                cadena=cadena.concat($("form[name='VinculacionIn31Form'] input[name='id_pre,"+k+","+l+"']").val(),","),cadena=cadena.concat($("form[name='VinculacionIn31Form'] input[name='val_A,"+k+","+l+"']").val(),",");
                cadena=cadena.concat($("form[name='VinculacionIn31Form'] input[name='val_B,"+k+","+l+"']").val(),","),cadena=cadena.concat($("form[name='VinculacionIn31Form'] input[name='val_C,"+k+","+l+"']").val(),",");
                cadena=cadena.concat($("form[name='VinculacionIn31Form'] input[name='val_D,"+k+","+l+"']").val(),","),cadena=cadena.concat($("form[name='VinculacionIn31Form'] input[name='val_E,"+k+","+l+"']").val(),",");
                cadena=cadena.concat($("form[name='VinculacionIn31Form'] input[name='val_F,"+k+","+l+"']").val(),","),cadena=cadena.concat($("form[name='VinculacionIn31Form'] input[name='val_G,"+k+","+l+"']").val(),"-");
            }     
        }       
    }         
    return cadena;  
}

function validarGuardado31(cu,fi,save,dofi){
    var aplica = $("[name='radioAplica']:checked").val();
    if (aplica === "0"){ //No aplica
        //SE GUARDARA LOS COMENTARIOS
        var comentario = $("form[name='VinculacionIn31Form'] input[name='comentario']").val();
         if(comentario != "Sin comentarios"){
             var indicador = "31";
             GuardarComentarios(comentario, indicador);
         }
        $().alerta("verde","Datos Guardados Correctamente.");
        return "NOAPLICA";
    }else{//aplica
        var cadena = "-"; //GUARDA LOS VALORES EN UNA CADENA
        var error=false;  
        if ($("form[name='VinculacionIn31Form'] input").hasClass("inputerror")){ //SI TIENE ERRORES
            $().alerta("rojo","Error al guardar. Por favor verifique los datos");
            return -1;
        }else{
            var temp;
            for(var j=1;j<cu;j++){
                for(var l=1;l<$("form[name='VinculacionIn31Form'] input[name='no_fil_niv,"+j+"']").val();l++){
                    if (l===1){
                        temp=parseInt($("form[name='VinculacionIn31Form'] input[name='tot_H,"+j+","+l+"']").val());
                    }else{
                        if (temp != parseInt($("form[name='VinculacionIn31Form'] input[name='tot_H,"+j+","+l+"']").val()) ){
                            error=true;
                            $("form[name='VinculacionIn31Form'] input[name^='tot_H,"+j+"']").addClass("inputalert").removeClass("inputok");
                            $().alerta("amarillo","Datos NO guardados. Los totales de la columna 'H' del cuadro 31."+j+" deben de coincidir en cada pregunta.");                      
                            return -1;//LOS TOTALES DE LA COLUMNA H NO COINCIDEN, SE DEVUELVE UN ERROR
                        }
                    }
                    if(parseInt($("form[name='VinculacionIn31Form'] input[name='tot_H,"+j+","+l+"']").val())<parseInt($("form[name='VinculacionIn31Form'] input[name='fin']").val())){
                        $("form[name='VinculacionIn31Form'] input[name='tot_H,"+j+","+l+"']").addClass("inputalert").removeClass("inputok");
                        error=true;      
                        $().alerta("amarillo","Datos NO guardados. El total de las columnas 'H' debe ser al menos el muestreo indicado '"+$("form[name='VinculacionIn31Form'] input[name='fin']").val());                      
                        return -1;
                    }else{
                        $("form[name='VinculacionIn31Form'] input[name='tot_H,"+j+","+l+"']").addClass("inputok").removeClass("inputalert");
                        error=false;
                    }
                }        
            }       
            if (error==false){
                cadena= operacionesVinculacion31(cu,fi,save,dofi);
                        //SE GUARDARA LOS COMENTARIOS
                   var comentario = $("form[name='VinculacionIn31Form'] input[name='comentario']").val();
                    if(comentario != "Sin comentarios"){
                        var indicador = "31";
                        GuardarComentarios(comentario, indicador);
                    }
                $().alerta("verde","Datos Guardados Correctamente.");
                return cadena;
            }else{
                return -1;
            }
        }  
    }
}

function blurVal21(){
    $('form[name="VinculacionIn31Form"] input[name^="no_fil_niv,"]').each(function(index, domEle) {
        $(domEle).blur();       
    });
}

