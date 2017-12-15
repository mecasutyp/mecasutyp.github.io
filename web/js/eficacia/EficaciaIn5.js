//Autor Daniel Ramirez Torres
function indicador5(control,cu,mo,ta,fi,dofi,save,tip){
    
    if (!/^([0-9])*$/.test(control.value)){ //SI EL VALOR NO ES VÁLIDO
        if (control.value==""){
            control.value=0;
            return;
        }
        $().alerta("rojo","El valor '"+ control.value +"' no es v&aacute;lido");
        $(control).removeClass("inputok").addClass("inputerror");
        return;
    }else{
        $(control).removeClass("inputerror").addClass("inputok");
        control.value=Number(control.value);    
        if(tip==1 && !$("form[name='EficaciaIn5Form'] input").hasClass("inputerror")){
              
            operacionesIn5(control,cu,mo,ta,fi,dofi,save);      
        }
        if(tip==2 && !$("form[name='EficaciaIn5Form'] input").hasClass("inputerror")){
            operacionesIn5_1(cu,save);      
        }
    }
}

function cargarCuerpo(niv,ani,c,tip){
    var jason={
        nivel:niv,
        anio:ani,
        tipo:tip
    };
    var div="";
    var url = "/Vistas/Eficacia/EficaciaIn5_Cuerpo.jsp";
    if(tip==1){
        div="cuadro"+c;
    }
    if(tip==2){
        div="cuadro_tipo2"+c;
    }
    if($("div[id='"+div+"']").hasClass("ui-accordion-content-active")==false || c==1){
        modal("Espere...","<img alt='MECASUT' src='images/ajax-loader.gif' /><br/> Cargando Indicador 5...");
        setTimeout(function(){
            $("div[id^=cuadro]").html("");           
            $("#"+div).load(url,jason,function(responseTxt,statusTxt,xhr){
                if(statusTxt=="success"){
                    $("#dialog-modal").dialog("close");
                    blur5(tip,niv);
                }
                if(statusTxt=="error"){
                    alert("Error: "+xhr.status+": "+xhr.statusText);
                }
            });
        },400);
    }   
}

function cargarPes(){
    modal("Espere...","<img alt='MECASUT' src='images/ajax-loader.gif' /><br/> Cargando Indicador 5...");
    cargarCuerpo($("form[name='EficaciaIn5Form'] input[name='nivel']").val(),$("form[name='EficaciaIn5Form'] input[name='anio']").val(),1,1)
}

function operacionesIn5(control,cu,mo,ta,fi,dorow,save){
    var c=$("form[name='EficaciaIn5Form'] input[name='tot_p"+cu+"']").val();
    var mod=$("form[name='EficaciaIn5Form'] input[name='tot_mo"+cu+"']").val();
    var modelos= $("form[name='EficaciaIn5Form'] input[name='numeromodelos']").val();
   
    var tot_in_niv = 0;
    var tot_eg_niv = 0;
    var tot_tasa = 0;
    var tingniv = 0
    var tegrniv = 0
    var x="-";       
    if(dorow==1)
    {
        for(var tab = 1; tab<=2; tab++) {            
            for (var i = 1; i < c; i++) {
                for(var mode = 1; mode<=mo;mode++) {
                    ing = parseInt($("form[name='EficaciaIn5Form'] input[name='in_"+cu+","+mode+","+tab+","+i+"']").val());
                    egr = parseInt($("form[name='EficaciaIn5Form'] input[name='eg_"+cu+","+mode+","+tab+","+i+"']").val());
                    if(tab == 1)
                    {
                        parseInt($("form[name='EficaciaIn5Form'] input[name='in_"+cu+","+mode+","+2+","+i+"']").val(ing));
                    }
                    tingniv += ing;
                    tegrniv += egr; 
                }                
                
                $("form[name='EficaciaIn5Form'] input[name='t_ing_"+cu+","+tab+","+i+"']").val(tingniv);
                $("form[name='EficaciaIn5Form'] input[name='t_egr_"+cu+","+tab+","+i+"']").val(tegrniv);

                tasaegreso = (getDecimal((tegrniv/tingniv*100)));
               
                if(!isFinite(tasaegreso)){
                    $("form[name='EficaciaIn5Form'] input[name='ta_egr_"+cu+","+tab+","+i+"']").val(0 + " %");
                }else{
                    $("form[name='EficaciaIn5Form'] input[name='ta_egr_"+cu+","+tab+","+i+"']").val(tasaegreso + " %");
                }    
                tingniv = 0;
                tegrniv = 0;
            }
            
        }
        
        var tingreso = 0;
        var tegreso = 0;
        //Suma de totales
        for(tab = 1; tab<=2; tab++){            
            for (mode = 1; mode<=mo;mode++){
                for(i = 1; i < c; i++){
                    ing = parseInt($("form[name='EficaciaIn5Form'] input[name='in_"+cu+","+mode+","+tab+","+i+"']").val());
                    egr = parseInt($("form[name='EficaciaIn5Form'] input[name='eg_"+cu+","+mode+","+tab+","+i+"']").val());
                    tot_in_niv += ing;
                    tot_eg_niv += egr; 
                }
                $("form[name='EficaciaIn5Form'] input[name='tot_ingreso_niv"+mode+","+tab+"']").val(tot_in_niv);
                $("form[name='EficaciaIn5Form'] input[name='tot_egreso_niv"+mode+","+tab+"']").val(tot_eg_niv);
                tingreso += tot_in_niv;
                tegreso += tot_eg_niv;
                tot_in_niv =0;
                tot_eg_niv = 0;
            }
            $("form[name='EficaciaIn5Form'] input[name='ingreso_"+cu+","+tab+"']").val(tingreso);
            $("form[name='EficaciaIn5Form'] input[name='egreso_"+cu+","+tab+"']").val(tegreso);
            tasaTot_ = (getDecimal(((tegreso/tingreso)*100)));
            if(!isFinite(tasaTot_)){
                $("form[name='EficaciaIn5Form'] input[name='tasaTot_"+cu+","+tab+"']").val(0 + " %");
            }
            else{
                $("form[name='EficaciaIn5Form'] input[name='tasaTot_"+cu+","+tab+"']").val(tasaTot_ + " %");
            }
            tingreso=0;
            tegreso=0;
        }
        
        var tasa_egreso = $("form[name='EficaciaIn5Form'] input[name='ta_egr_1,1,"+fi+"']").val();
        var tasa_titulacion =  $("form[name='EficaciaIn5Form'] input[name='ta_egr_1,2,"+fi+"']").val(); 
        
        if(parseInt(tasa_egreso) < parseInt(tasa_titulacion)){
            $().alerta("rojo","No puede ser mayor la tasa de titulaci&oacute;n a la de egreso. ");
            $(control).removeClass("inputok").addClass("inputerror");  
            return "error";
        }else{
            $(control).removeClass("inputerror").addClass("inputok");
        }
    }
    else
    {
        var cuatri=0;
        var nivel;
        for(var k=1;k<c;k++){
            for(var l=1;l<mod;l++){
                nivel= $("form[name='EficaciaIn5Form'] input[name='id_niv"+cu+"']").val();
               if(nivel==1 || nivel==3){
                for(var i=0;i<$("form[name='EficaciaIn5Form'] input[name='radiocuatri"+cu+""+mo+"']").length;i++){
                     if ($("form[name='EficaciaIn5Form'] input[name='radiocuatri"+cu+""+mo+"']")[0].checked==true){
                           cuatri=1;//ENERO-ABRIL
                     }
                     if ($("form[name='EficaciaIn5Form'] input[name='radiocuatri"+cu+""+mo+"']")[1].checked==true){
                             cuatri=2;//MAYO-AGOSTO
                     }
                     if ($("form[name='EficaciaIn5Form'] input[name='radiocuatri"+cu+""+mo+"']")[2].checked==true){
                             cuatri=3;//SEPTIEMBRE-DICIEMBRE
                     }

                 }
               }
                x+= $("form[name='EficaciaIn5Form'] input[name='id_niv"+cu+"']").val()+","+$("form[name='EficaciaIn5Form'] input[name='id_prog"+cu+","+l+",1,"+k+"']").val()+
                ","+$("form[name='EficaciaIn5Form'] input[name='id_mod"+cu+","+l+",1,"+k+"']").val()+
                ","+mo+
                ","+$("form[name='EficaciaIn5Form'] input[name='in_"+cu+","+l+",1,"+k+"']").val()+
                ","+$("form[name='EficaciaIn5Form'] input[name='eg_"+cu+","+l+",1,"+k+"']").val()+
                ","+$("form[name='EficaciaIn5Form'] input[name='eg_"+cu+","+l+",2,"+k+"']").val()+
                ","+cuatri+"-";
            }
        }
        if(save==1 && $("form[name='EficaciaIn5Form'] input").hasClass("inputerror")){
            //$().alerta('rojo', 'Error al guardar. Por favor verifique los datos.');
            return "Error";            
        }else{
            // $().alerta('verde', 'Datos guardados correctamente.');
            return x;
        } 
    }
   
}

function operacionesIn5_1(niv,save){
    var noF=$("form[name='EficaciaIn5Form'] input[name='totalFil_"+niv+"']").val();
    var tit_Ut=0;
    var tit_D=0;
    var x="x";
    if(parseInt(save)==0){
        for(var i =1; i<noF;i++){
            tit_Ut+=parseInt($("form[name='EficaciaIn5Form'] input[name='valU_"+niv+","+i+"']").val());
            tit_D+=parseInt($("form[name='EficaciaIn5Form'] input[name='valD_"+niv+","+i+"']").val());
            if(parseInt($("form[name='EficaciaIn5Form'] input[name='valU_"+niv+","+i+"']").val())>0){
                //tot_Por+=getDecimal((tit_D/tit_Ut*100));
                $("form[name='EficaciaIn5Form'] input[name='porcentDGP_"+niv+","+i+"']").val(getDecimal(($("form[name='EficaciaIn5Form'] input[name='valD_"+niv+","+i+"']").val()/$("form[name='EficaciaIn5Form'] input[name='valU_"+niv+","+i+"']").val()*100)) + " %");
            }else{
                $("form[name='EficaciaIn5Form'] input[name='porcentDGP_"+niv+","+i+"']").val(0 + " %");
            }
        }
        $("form[name='EficaciaIn5Form'] input[name='totU_"+niv+"']").val(tit_Ut);
        $("form[name='EficaciaIn5Form'] input[name='totD_"+niv+"']").val(tit_D);
        if(tit_Ut>0){
            $("form[name='EficaciaIn5Form'] input[name='totPorcentDGP_"+niv+"']").val(getDecimal(((tit_D/tit_Ut)*100)) + " %");
        }
        else{
            $("form[name='EficaciaIn5Form'] input[name='totPorcentDGP_"+niv+"']").val(0 + " %");
        }
        tit_Ut=0;
        tit_D=0;
    }
    if(parseInt(save)==1){
        for(var j =1; j<noF;j++){
            x+=niv+","+
            $("form[name='EficaciaIn5Form'] input[name='anio"+j+"']").val()+","+
            $("form[name='EficaciaIn5Form'] input[name='valU_"+niv+","+j+"']").val()+","+
            $("form[name='EficaciaIn5Form'] input[name='valD_"+niv+","+j+"']").val()+"x";
        }
        if(save==1 && $("form[name='EficaciaIn5Form'] input").hasClass("inputerror")){
            //$().alerta('rojo', 'Error al guardar. Por favor verifique los datos.');
            return "Error";
        }else{
            // $().alerta('verde', 'Datos guardados correctamente.');
            return x;
        }
    }
}

function blur5(tip,niv){
    if(tip==1){      
        $("form[name='EficaciaIn5Form'] input[name='in_1,1,1,1']").blur();
        $("form[name='EficaciaIn5Form'] input[name='in_1,1,2,1']").blur();     
    }
    if(tip==2){     
        $("form[name='EficaciaIn5Form'] input[name='valU_"+niv+",1']").blur();
        $('#button2').button('refresh');
    }
}

function cambiarFuncion(anio,tipo,nivel){
    alert("anio:"+anio+"  tipo:"+tipo+"   nivel:"+nivel)
    if(tipo==1){
        document.getElementById('button5').onclick = "retrieveURL('/EficaciaIn5.msut?valores='+operacionesIn5(1,"+anio+",0,0,0,1),'EficaciaIn5Form')";
    }
    if(tipo==2){
        document.getElementById('button5').onclick = "retrieveURL('/EficaciaIn5.msut?valores='+operacionesIn5_1("+nivel+",1),'EficaciaIn5Form')";
    }
}

function enviaarchivo(){
    var existearchivo=verificararchivo("EficaciaIn5Form");
    var tamanomaximo=2097152;
    var seenvia=true;
    if(existearchivo){
        if($("form[name='EficaciaIn5Form'] input[name='archivo']")[0].files[0].size > tamanomaximo){
            $().alerta('rojo', 'Error al guardar. El tama&ntilde;o del archivo es mayor al permitido.');
              $("form[name='EficaciaIn5Form'] input[name='archivo']").removeClass("inputok").addClass("inputerror");
              seenvia=false;
        }else{   
            var filexext=$("form[name='EficaciaIn5Form'] input[name='archivo']")[0].value;
            var extension = (filexext.substring(filexext.lastIndexOf("."))).toLowerCase(); 
             var base64txt="";
            base64( $("form[name='EficaciaIn5Form'] input[name='archivo']")[0], function(data){
                base64txt=data.base64;
                var universidad=$("form[name='EficaciaIn5Form'] input[name='nomuni']").val();  
                var IdUni=$("form[name='EficaciaIn5Form'] input[name='IdUni']").val();  
                var IdPer=$("form[name='EficaciaIn5Form'] input[name='IdPer']").val();  
                if(parseInt(data.size) < tamanomaximo){//SI ES MENOR A 2 MB (2097152 BYTES) SE ENVIARA
                     enviarcorreo(base64txt, universidad, "5", extension, IdUni, IdPer);
                }
            }) 
        }   
    }else{
        $().alerta('rojo', 'Error al guardar. Es necesario enviar un archivo.');
        seenvia=false;
    }
}
