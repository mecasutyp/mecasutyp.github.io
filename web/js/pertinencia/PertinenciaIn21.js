//DEVUELVE EL NÚMERO DE CONTROLES EN EL DOCUMENTO
//Rebuild 11/03/2013 Daniel Ramírez Torres
//Rebuild 18/06/2013 Daniel Ramírez Torres

function cargarCuerpo21 (idServicio, poblacion, primer){
    var url = "/Vistas/Pertinencia/PertinenciaIn21_Cuerpo.jsp";
    var parametros = {
        "idServicio" : idServicio,
        "poblacion" : poblacion
    }
    if ( !$("#contenedorServicio_"+idServicio).hasClass("ui-accordion-content-active") || primer){ //acordion activo
        $("div[id^=contenedorServicio_]").html("");   
        $("#dialog-modal").dialog("open");
        $("#contenedorServicio_"+idServicio).load(url,parametros,function(responseTxt,statusTxt,xhr){
            if(statusTxt=="success"){
                $("#dialog-modal").dialog("close");
            }
            if(statusTxt=="error"){
                alert("Error: "+xhr.status+": "+xhr.statusText);
            }
        });
    }
}

function indicador21(control,cuadro,fil,save,dofil){
    if (control.value==""){
        control.value=0;
        return;
    }
    if (!/^([0-9])*$/.test(control.value)){ //SI EL VALOR NO ES VÁLIDO
        $().alerta("rojo","El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;
    }else{
        $(control).removeClass("inputerror").addClass("inputok");
        control.value=Number(control.value);
        if (!$("div[id='contenedorServicio_"+cuadro+"'] input").hasClass("inputerror")){ 
            operaciones_servicios(cuadro,fil,save,dofil);
        }
    }
}

function operaciones_servicios(cuadro,fil,save,dofil){  
    var cadena=""
    if(parseInt(save)==0){
        if (!$("div[id='contenedorServicio_"+cuadro+"'] input").hasClass("inputerror")){ 
            var noProg=$("form[name='PertinenciaIn21Form'] input[id='noProg"+cuadro+"']").val(),total_A=0,total_B=0,total_C=0,total_D=0,total_E=0,total_F=0,total_G=0,total_H=0,total_I=0,total_J=0,total_K_5=0,total_K_10=0; 
            for(var i=1;i<=noProg;i++){ 
                //realiza operaciones por fila
                if(parseInt(dofil)==1){
                    //Suma los valores y asigna el primer total
                    parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preH,"+cuadro+","+fil+"']").val(parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preA,"+cuadro+","+fil+"']").val())+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preB,"+cuadro+","+fil+"']").val())+ parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preC,"+cuadro+","+fil+"']").val())+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preD,"+cuadro+","+fil+"']").val())+ parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preE,"+cuadro+","+fil+"']").val())+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preF,"+cuadro+","+fil+"']").val())+ parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preG,"+cuadro+","+fil+"']").val())));
                    //Suma los valores y los asigna el segundo total
                    parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preI,"+cuadro+","+fil+"']").val(parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preA,"+cuadro+","+fil+"']").val())+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preB,"+cuadro+","+fil+"']").val())+parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preC,"+cuadro+","+fil+"']").val())+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preD,"+cuadro+","+fil+"']").val())+parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preE,"+cuadro+","+fil+"']").val())));
                    //REALIZA OPERACION 3
                    parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preJ,"+cuadro+","+fil+"']").val(parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preA,"+cuadro+","+fil+"']").val()*5)+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preB,"+cuadro+","+fil+"']").val()*4)+parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preC,"+cuadro+","+fil+"']").val()*3)+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preD,"+cuadro+","+fil+"']").val()*2)+parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preE,"+cuadro+","+fil+"']").val()*1)));
                    //REALIZA OPERACION 4
                    if (parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preJ,"+cuadro+","+fil+"']").val())!=0 && parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preI,"+cuadro+","+fil+"']").val())!=0){
                        $("form[name='PertinenciaIn21Form'] input[id='no_preK_5,"+cuadro+","+fil+"']").val(getDecimal(parseFloat($("form[name='PertinenciaIn21Form'] input[id='no_preJ,"+cuadro+","+fil+"']").val())/
                            parseFloat($("form[name='PertinenciaIn21Form'] input[id='no_preI,"+cuadro+","+fil+"']").val())));
                        //REALIZA OPERACION 5
                        $("form[name='PertinenciaIn21Form'] input[id='no_preK_10,"+cuadro+","+fil+"']").val(getDecimal(parseFloat($("form[name='PertinenciaIn21Form'] input[id='no_preK_5,"+cuadro+","+fil+"']").val())*parseFloat(2)));
                    }else{
                        $("form[name='PertinenciaIn21Form'] input[id='no_preK_10,"+cuadro+","+fil+"']").val(0),$("form[name='PertinenciaIn21Form'] input[id='no_preK_5,"+cuadro+","+fil+"']").val(0);
                    }
        
                }
                if(parseInt(dofil)==0 && parseInt(fil)==0){
                    //Operaciones al cargar
                    parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preH,"+cuadro+","+i+"']").val(parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preA,"+cuadro+","+i+"']").val())+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preB,"+cuadro+","+i+"']").val())+ parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preC,"+cuadro+","+i+"']").val())+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preD,"+cuadro+","+i+"']").val())+ parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preE,"+cuadro+","+i+"']").val())+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preF,"+cuadro+","+i+"']").val())+ parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preG,"+cuadro+","+i+"']").val())));
                    //Suma los valores y los asigna el segundo total
                    parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preI,"+cuadro+","+i+"']").val(parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preA,"+cuadro+","+i+"']").val())+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preB,"+cuadro+","+i+"']").val())+parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preC,"+cuadro+","+i+"']").val())+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preD,"+cuadro+","+i+"']").val())+parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preE,"+cuadro+","+i+"']").val())));
                    //REALIZA OPERACION 3
                    parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preJ,"+cuadro+","+i+"']").val(parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preA,"+cuadro+","+i+"']").val()*5)+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preB,"+cuadro+","+i+"']").val()*4)+parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preC,"+cuadro+","+i+"']").val()*3)+
                        parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preD,"+cuadro+","+i+"']").val()*2)+parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preE,"+cuadro+","+i+"']").val()*1)));
                    //REALIZA OPERACION 4
                    if (parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preJ,"+cuadro+","+i+"']").val())!=0 && parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preI,"+cuadro+","+i+"']").val())!=0){
                        $("form[name='PertinenciaIn21Form'] input[id='no_preK_5,"+cuadro+","+i+"']").val(getDecimal(parseFloat($("form[name='PertinenciaIn21Form'] input[id='no_preJ,"+cuadro+","+i+"']").val())/parseFloat($("form[name='PertinenciaIn21Form'] input[id='no_preI,"+cuadro+","+i+"']").val())));
                        //REALIZA OPERACION 5
                        $("form[name='PertinenciaIn21Form'] input[id='no_preK_10,"+cuadro+","+i+"']").val(getDecimal(parseFloat($("form[name='PertinenciaIn21Form'] input[id='no_preK_5,"+cuadro+","+i+"']").val())*parseFloat(2)));
                    }else{
                        $("form[name='PertinenciaIn21Form'] input[id='no_preK_10,"+cuadro+","+i+"']").val(0),$("form[name='PertinenciaIn21Form'] input[id='no_preK_5,"+cuadro+","+i+"']").val(0);
                    }
                }         
                total_A+= parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preA,"+cuadro+","+i+"']").val()),total_B+= parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preB,"+cuadro+","+i+"']").val()),total_C+= parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preC,"+cuadro+","+i+"']").val()),
                total_D+= parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preD,"+cuadro+","+i+"']").val()),total_E+= parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preE,"+cuadro+","+i+"']").val()),total_F+= parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preF,"+cuadro+","+i+"']").val()),
                total_G+= parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preG,"+cuadro+","+i+"']").val()),total_H+= parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preH,"+cuadro+","+i+"']").val()),total_I+= parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preI,"+cuadro+","+i+"']").val()),
                total_J+= parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preJ,"+cuadro+","+i+"']").val()),total_K_5+= parseFloat($("form[name='PertinenciaIn21Form'] input[id='no_preK_5,"+cuadro+","+i+"']").val()),total_K_10+= parseFloat($("form[name='PertinenciaIn21Form'] input[id='no_preK_10,"+cuadro+","+i+"']").val());
            }
            $("form[name='PertinenciaIn21Form'] input[id='TotalA,"+cuadro+"']").val(total_A),$("form[name='PertinenciaIn21Form'] input[id='TotalB,"+cuadro+"']").val(total_B), $("form[name='PertinenciaIn21Form'] input[id='TotalC,"+cuadro+"']").val(total_C),$("form[name='PertinenciaIn21Form'] input[id='TotalD,"+cuadro+"']").val(total_D),
            $("form[name='PertinenciaIn21Form'] input[id='TotalE,"+cuadro+"']").val(total_E), $("form[name='PertinenciaIn21Form'] input[id='TotalF,"+cuadro+"']").val(total_F),$("form[name='PertinenciaIn21Form'] input[id='TotalG,"+cuadro+"']").val(total_G),$("form[name='PertinenciaIn21Form'] input[id='TotalH,"+cuadro+"']").val(total_H),
            $("form[name='PertinenciaIn21Form'] input[id='TotalI,"+cuadro+"']").val(total_I),$("form[name='PertinenciaIn21Form'] input[id='TotalJ,"+cuadro+"']").val(total_J)
            
            //DISTRIBUCION PORCENTUAL
          var col_A_D=0, col_B_D=0, col_C_D=0, col_D_D=0, col_E_D=0, col_F_D=0, col_G_D=0, col_I_D=0.0, col_H_TES=0;
          col_A_D=getDecimal((total_A/total_H)*100);
          col_B_D=getDecimal((total_B/total_H)*100);
          col_C_D=getDecimal((total_C/total_H)*100);
          col_D_D=getDecimal((total_D/total_H)*100);
          col_E_D=getDecimal((total_E/total_H)*100);
          col_F_D=getDecimal((total_F/total_H)*100);
          col_G_D=getDecimal((total_G/total_H)*100);
        if(isNaN(col_A_D)){ col_A_D= "0 %"; }else{col_A_D= col_A_D+" %";}//SI EL RESULTADO ES NAN
        if(isNaN(col_B_D)){ col_B_D= "0 %"; }else{col_B_D= col_B_D+" %";}
        if(isNaN(col_C_D)){ col_C_D= "0 %"; }else{col_C_D= col_C_D+" %";}
        if(isNaN(col_D_D)){ col_D_D= "0 %"; }else{col_D_D= col_D_D+" %";}
        if(isNaN(col_E_D)){ col_E_D= "0 %"; }else{col_E_D= col_E_D+" %";}
        if(isNaN(col_F_D)){ col_F_D= "0 %"; }else{col_F_D= col_F_D+" %";}
        if(isNaN(col_G_D)){ col_G_D= "0 %"; }else{col_G_D= col_G_D+" %";}
        var num_Preguntas=parseInt($("form[name='PertinenciaIn21Form'] input[id='nopreguntas-"+cuadro+"']").val());
        
        col_H_TES=$("form[name='PertinenciaIn21Form'] input[id='TotalH,"+cuadro+"']").val();
        col_I_D=getDecimal( (((total_A+total_B) )/ col_H_TES)*100  );
          if(col_I_D == Number.POSITIVE_INFINITY || col_I_D == Number.NEGATIVE_INFINITY){//ES RESULTADO INFINITO
            col_I_D="";
        }else{
            if(isNaN(col_I_D)){ 
                col_I_D= ""; 
            }else{
                col_I_D= col_I_D+" %";
            }
        }
        $("form[name='PertinenciaIn21Form'] input[name='tv_"+cuadro+"-1-d-ms']").val(col_A_D), $("form[name='PertinenciaIn21Form'] input[name='tv_"+cuadro+"-2-d-s']").val(col_B_D),
        $("form[name='PertinenciaIn21Form'] input[name='tv_"+cuadro+"-3-d-rs']").val(col_C_D), $("form[name='PertinenciaIn21Form'] input[name='tv_"+cuadro+"-4-d-ps']").val(col_D_D),
        $("form[name='PertinenciaIn21Form'] input[name='tv_"+cuadro+"-5-d-ns']").val(col_E_D), $("form[name='PertinenciaIn21Form'] input[name='tv_"+cuadro+"-6-d-na']").val(col_F_D),
        $("form[name='PertinenciaIn21Form'] input[name='tv_"+cuadro+"-7-d-ne']").val(col_G_D), $("form[name='PertinenciaIn21Form'] input[name='tv_"+cuadro+"-9-d-tes']").val(col_I_D);
          
            if(parseInt(total_I)>0){
                $("form[name='PertinenciaIn21Form'] input[id='TotalK_5,"+cuadro+"']").val(getDecimal(parseFloat(total_J/total_I)));
            }else{
                $("form[name='PertinenciaIn21Form'] input[id='TotalK_5,"+cuadro+"']").val(0);
            }

        $("form[name='PertinenciaIn21Form'] input[id='TotalK_10,"+cuadro+"']").val(parseFloat($("form[name='PertinenciaIn21Form'] input[id='TotalK_5,"+cuadro+"']").val())*2);         
            total_A=0,total_A=0,total_B=0,total_C=0,total_D=0,total_E=0,total_F=0,total_G=0,total_H=0,total_I=0,total_J=0,total_K_5=0,total_K_10=0;    
            operaciones_servicios_tot_21();    
        }
    }
    if(parseInt(save)==1){        
        cadena=guardarPertinenciaIn21(cuadro);
        return cadena;
    }    
}


//funcion de los cuadros y servicio totales 
function operaciones_servicios_tot_21(){
    var num_servicios=$("form[name='PertinenciaIn21Form'] input[name='num_servicios']").val(),tot_base_5=0,tot_base_10=0,cont=0,TotalA=0,TotalB=0,TotalC=0,TotalD=0,TotalE=0,
    TotalF=0,TotalG=0,TotalH=0,TotalI=0,TotalJ=0,TotalK_5=0,TotalK_10=0;
    for(var i=1;i<=num_servicios;i++){        
        TotalA+=parseInt($("form[name='PertinenciaIn21Form'] input[id='TotalA,"+i+"']").val()),
        TotalB+=parseInt($("form[name='PertinenciaIn21Form'] input[id='TotalB,"+i+"']").val()),
        TotalC+=parseInt($("form[name='PertinenciaIn21Form'] input[id='TotalC,"+i+"']").val()),
        TotalD+=parseInt($("form[name='PertinenciaIn21Form'] input[id='TotalD,"+i+"']").val()),
        TotalE+=parseInt($("form[name='PertinenciaIn21Form'] input[id='TotalE,"+i+"']").val()),
        TotalF+=parseInt($("form[name='PertinenciaIn21Form'] input[id='TotalF,"+i+"']").val()),
        TotalG+=parseInt($("form[name='PertinenciaIn21Form'] input[id='TotalG,"+i+"']").val()),
        TotalH+=parseInt($("form[name='PertinenciaIn21Form'] input[id='TotalH,"+i+"']").val()),
        TotalI+=parseInt($("form[name='PertinenciaIn21Form'] input[id='TotalI,"+i+"']").val()), 
        TotalJ+=parseInt($("form[name='PertinenciaIn21Form'] input[id='TotalJ,"+i+"']").val()),        
        TotalK_10+=parseInt($("form[name='PertinenciaIn21Form'] input[id='TotalK_10,"+i+"']").val());
    }              

$("form[name='PertinenciaIn21Form'] input[id='TotalA']").val(TotalA),$("form[name='PertinenciaIn21Form'] input[id='TotalB']").val(TotalB),$("form[name='PertinenciaIn21Form'] input[id='TotalC']").val(TotalC),
    $("form[name='PertinenciaIn21Form'] input[id='TotalD']").val(TotalD),$("form[name='PertinenciaIn21Form'] input[id='TotalE']").val(TotalE), $("form[name='PertinenciaIn21Form'] input[id='TotalF']").val(TotalF),
    $("form[name='PertinenciaIn21Form'] input[id='TotalG']").val(TotalG),$("form[name='PertinenciaIn21Form'] input[id='TotalH']").val(TotalH),$("form[name='PertinenciaIn21Form'] input[id='TotalI']").val(TotalI),
    $("form[name='PertinenciaIn21Form'] input[id='TotalJ']").val(TotalJ); 
    
    
    if(parseInt(TotalI)==0){
        $("form[name='PertinenciaIn21Form'] input[id='TotalK_5']").val(0),$("form[name='PertinenciaIn21Form'] input[id='TotalK_10']").val(0);  
        $("form[name='PertinenciaIn21Form'] input[name='TotalFinal1']").val(0); 
        $("form[name='PertinenciaIn21Form'] input[name='TotalFinal2']").val(0); 
    }else{    
        
        $("form[name='PertinenciaIn21Form'] input[id='TotalK_5']").val(getDecimal(parseFloat(TotalJ/TotalI))),$("form[name='PertinenciaIn21Form'] input[id='TotalK_10']").val(getDecimal(parseFloat( $("form[name='PertinenciaIn21Form'] input[name='TotalFinal1']").val()*2))); 
        $("form[name='PertinenciaIn21Form'] input[name='TotalFinal1']").val(getDecimal(parseFloat(TotalJ/TotalI))),$("form[name='PertinenciaIn21Form'] input[name='TotalFinal2']").val(getDecimal(parseFloat( $("form[name='PertinenciaIn21Form'] input[name='TotalFinal1']").val()*2)));
    }
   
}



function recuperar_Valores(cuadro){
    var cadena="-";
    var noProg=$("form[name='PertinenciaIn21Form'] input[id='noProg"+cuadro+"']").val(); 
    for(var j=1;j<=noProg;j++){ 
        cadena=cadena.concat($("form[name='PertinenciaIn21Form'] input[id='no_preA,"+cuadro+","+j+"']").attr("Name"),",",            
            $("form[name='PertinenciaIn21Form'] input[id='no_preA,"+cuadro+","+j+"']").val(),",",$("form[name='PertinenciaIn21Form'] input[id='no_preB,"+cuadro+","+j+"']").val(),",",
            $("form[name='PertinenciaIn21Form'] input[id='no_preC,"+cuadro+","+j+"']").val(),",",$("form[name='PertinenciaIn21Form'] input[id='no_preD,"+cuadro+","+j+"']").val(),",",
            $("form[name='PertinenciaIn21Form'] input[id='no_preE,"+cuadro+","+j+"']").val(),",",$("form[name='PertinenciaIn21Form'] input[id='no_preF,"+cuadro+","+j+"']").val(),",",
            $("form[name='PertinenciaIn21Form'] input[id='no_preG,"+cuadro+","+j+"']").val());
        cadena=cadena.concat("-")
    }    
    return cadena;
}

function guardarPertinenciaIn21(cuadro){
    var num_servicios=$("form[name='PertinenciaIn21Form'] input[name='num_servicios']").val(),x="",error=false;  
    var muestreo=$("form[name='PertinenciaIn21Form'] input[name='fin']").val();
    
    var noProg=$("form[name='PertinenciaIn21Form'] input[id='noProg"+cuadro+"']").val();
    var temp;
    for(var j=1;j<=noProg;j++){
        if (j===1){
            temp=parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preH,"+cuadro+","+j+"']").val());
        }else{
            if (temp != parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preH,"+cuadro+","+j+"']").val()) ){
                error=true;
                $("form[name='PertinenciaIn21Form'] input[id^='no_preH,"+cuadro+"']").addClass("inputalert").removeClass("inputok");
                $().alerta("amarillo","Datos NO guardados. Los totales de la columna 'H' deben de coincidir en cada pregunta.");
                x= "Error";//LOS TOTALES DE LA COLUMNA H NO COINCIDEN, SE DEVUELVE UN ERROR
            }
        }
        if(parseInt($("form[name='PertinenciaIn21Form'] input[id='no_preH,"+cuadro+","+j+"']").val())<parseInt(muestreo)){
            error=true;
            $("form[name='PertinenciaIn21Form'] input[id='no_preH,"+cuadro+","+j+"']").removeClass("inputok").addClass("inputalert");
            $().alerta('amarillo', 'El total de la columna H debe ser al menos el muestreo "'+muestreo+'". Por favor verifique los datos.'); 
            x= "Error"; 
                 
        }else{
            $("form[name='PertinenciaIn21Form'] input[id='no_preH,"+cuadro+","+j+"']").removeClass("inputalert").addClass("inputok");
           
        }     
    }      
    if ($("div[id='contenedorServicio_"+cuadro+"'] input").hasClass("inputerror")  ||error==true|| $("div[id='contenedorServicio_"+cuadro+"'] input").hasClass("inputalert")){
        x="Error";
    }    
    else{
        
        x=recuperar_Valores(cuadro);
           
    }   
        return x;
}


function blurVal21(num){
    if(num==undefined){        
        $('form[name="PertinenciaIn21Form"] input[id="TotalA,1"]').blur();       
    }else{
        $("form[name='PertinenciaIn21Form'] input[id='TotalA,"+num+"']").blur();       
    }
}

function operacionesFinales(){ 
    $( "#dialog-cargando" ).dialog({
        height: 150,
        modal: true,
        disabled: true,
        closeText: 'hide',
        show: "fade",
        hide: "fade"
    }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();  
    setTimeout(function(){
        $('form[name="PertinenciaIn21Form"] input[id^="TotalA,"]').each(function(index, domEle) {
            $(domEle).blur();      
            $( "#dialog-cargando" ).dialog("close");
        });
    },500);   
}

