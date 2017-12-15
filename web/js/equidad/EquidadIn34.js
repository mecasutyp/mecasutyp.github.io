//Author: Daniel Ramírez Torres
function indicador34(control,niv){
    if (control.value == "")
        control.value = 0;
    if (!/^([0-9])*$/.test(control.value)){
        $().alerta("amarillo", "El valor '".concat(control.value).concat("' no es v&aacute;lido"));
        $(control).addClass("inputerror").removeClass("inputok");
        $('#EquidadIn34Form').globo("enable").globo("update", "<center>Por favor revise los datos <br/> de este indicador</center>").globo("show")
    }
    else{   
        control.value=Number(control.value);
        $(control).addClass("inputok").removeClass("inputerror");
        if (!$("form[name='EquidadIn34Form'] input").hasClass("inputerror")){ 
            operaciones_Equidad34(niv);
        }
    }
}

function operaciones_Equidad34(niv){
    var num_pro=$("form[name='EquidadIn34Form'] input[name='num_pro"+niv+"']").val(),tv_ing_h=0,tv_ing_m=0,tv_re_ing_h=0,tv_re_ing_m=0,tv_tot_re_ing=0,tv_tot_h=0,tv_tot_m=0;
    for(var i=1; i<=num_pro;i++){
        $("form[name='EquidadIn34Form'] input[id='tot_ing,"+niv+","+i+"']").val(parseInt($("form[name='EquidadIn34Form'] input[id='ing_h,"+niv+","+i+"']").val())+parseInt($("form[name='EquidadIn34Form'] input[id='ing_m,"+niv+","+i+"']").val())),
        $("form[name='EquidadIn34Form'] input[name='tot_re_ing,"+niv+","+i+"']").val(parseInt($("form[name='EquidadIn34Form'] input[id='re_ing_h,"+niv+","+i+"']").val())+parseInt($("form[name='EquidadIn34Form'] input[id='re_ing_m,"+niv+","+i+"']").val())),
        $("form[name='EquidadIn34Form'] input[id='tot_h,"+niv+","+i+"']").val(parseInt($("form[name='EquidadIn34Form'] input[id='ing_h,"+niv+","+i+"']").val())+parseInt($("form[name='EquidadIn34Form'] input[id='re_ing_h,"+niv+","+i+"']").val())),
        $("form[name='EquidadIn34Form'] input[id='tot_m,"+niv+","+i+"']").val(parseInt($("form[name='EquidadIn34Form'] input[id='ing_m,"+niv+","+i+"']").val())+parseInt($("form[name='EquidadIn34Form'] input[id='re_ing_m,"+niv+","+i+"']").val())),
        $("form[name='EquidadIn34Form'] input[id='total_h_m,"+niv+","+i+"']").val(parseInt($("form[name='EquidadIn34Form'] input[id='tot_m,"+niv+","+i+"']").val())+parseInt($("form[name='EquidadIn34Form'] input[id='tot_h,"+niv+","+i+"']").val()));
        tv_ing_h+=parseInt($("form[name='EquidadIn34Form'] input[id='ing_h,"+niv+","+i+"']").val()),tv_ing_m+=parseInt($("form[name='EquidadIn34Form'] input[id='ing_m,"+niv+","+i+"']").val()),
        tv_re_ing_h+=parseInt($("form[name='EquidadIn34Form'] input[id='re_ing_h,"+niv+","+i+"']").val()),tv_re_ing_m+=parseInt($("form[name='EquidadIn34Form'] input[id='re_ing_m,"+niv+","+i+"']").val()),
        tv_tot_re_ing+=parseInt($("form[name='EquidadIn34Form'] input[name='tot_re_ing,"+niv+","+i+"']").val()),tv_tot_h+=parseInt($("form[name='EquidadIn34Form'] input[id='tot_h,"+niv+","+i+"']").val()),
        tv_tot_m+=parseInt($("form[name='EquidadIn34Form'] input[id='tot_m,"+niv+","+i+"']").val());       
    }
    $("form[name='EquidadIn34Form'] input[name='tv_ing_h,"+niv+"']").val(tv_ing_h),$("form[name='EquidadIn34Form'] input[name='tv_ing_m,"+niv+"']").val(tv_ing_m),    
    $("form[name='EquidadIn34Form'] input[name='tv_tot_ing,"+niv+"']").val(tv_ing_h+tv_ing_m),$("form[name='EquidadIn34Form'] input[name='tv_re_ing_h,"+niv+"']").val(tv_re_ing_h),
    $("form[name='EquidadIn34Form'] input[name='tv_re_ing_m,"+niv+"']").val(tv_re_ing_m),$("form[name='EquidadIn34Form'] input[name='tv_tot_re_ing,"+niv+"']").val(tv_re_ing_h+tv_re_ing_m),    
    $("form[name='EquidadIn34Form'] input[name='tv_tot_h,"+niv+"']").val(tv_tot_h),$("form[name='EquidadIn34Form'] input[name='tv_tot_m,"+niv+"']").val(tv_tot_m);    
    $("form[name='EquidadIn34Form'] input[name='tv_total_h_m,"+niv+"']").val(tv_tot_h+tv_tot_m);             
    tv_ing_h=0,tv_re_ing_h=0,tv_re_ing_m=0,tv_tot_re_ing=0,tv_tot_h=0,tv_tot_m=0,tv_ing_m=0;
    operaciones_Totales();    
}


function operaciones_Totales(){
    var num_niv=$("form[name='EquidadIn34Form'] input[name='niveles']").val(),tv_ing_h=0,tv_ing_m=0,tv_tot_ing=0,tv_re_ing_h=0,tv_re_ing_m=0,tv_tot_re_ing=0,tv_tot_h=0,tv_tot_m=0,tv_total_h_m=0;
    for(var i=1; i<num_niv;i++){
        tv_ing_h+=parseInt($("form[name='EquidadIn34Form'] input[name='tv_ing_h,"+i+"']").val()),tv_ing_m+=parseInt($("form[name='EquidadIn34Form'] input[name='tv_ing_m,"+i+"']").val()),
        tv_tot_ing+=parseInt($("form[name='EquidadIn34Form'] input[name='tv_tot_ing,"+i+"']").val()),       
        tv_re_ing_h+=parseInt($("form[name='EquidadIn34Form'] input[name='tv_re_ing_h,"+i+"']").val()),tv_re_ing_m+=parseInt($("form[name='EquidadIn34Form'] input[name='tv_re_ing_m,"+i+"']").val()),
        tv_tot_re_ing+=parseInt($("form[name='EquidadIn34Form'] input[name='tv_tot_re_ing,"+i+"']").val()),  
        tv_tot_h+=parseInt($("form[name='EquidadIn34Form'] input[name='tv_tot_h,"+i+"']").val()),tv_tot_m+=parseInt($("form[name='EquidadIn34Form'] input[name='tv_tot_m,"+i+"']").val()),
        tv_total_h_m+=parseInt($("form[name='EquidadIn34Form'] input[name='tv_total_h_m,"+i+"']").val());
       
       if(parseInt($("form[name='EquidadIn34Form'] input[name='tv_tot_ing,"+i+"']").val())>0){
            $("form[name='EquidadIn34Form'] input[name='tv_p1,"+i+"']").val(getDecimal(parseFloat($("form[name='EquidadIn34Form'] input[name='tv_ing_h,"+i+"']").val()/$("form[name='EquidadIn34Form'] input[name='tv_tot_ing,"+i+"']").val()*100)));
        }else{
            $("form[name='EquidadIn34Form'] input[name='tv_p1,"+i+"']").val(0);
        }
        
        if(parseInt($("form[name='EquidadIn34Form'] input[name='tv_tot_ing,"+i+"']").val())>0){
            
            $("form[name='EquidadIn34Form'] input[name='tv_p2,"+i+"']").val(getDecimal(parseFloat($("form[name='EquidadIn34Form'] input[name='tv_ing_m,"+i+"']").val()/$("form[name='EquidadIn34Form'] input[name='tv_tot_ing,"+i+"']").val()*100)));
        }else{
            $("form[name='EquidadIn34Form'] input[name='tv_p2,"+i+"']").val(0);            
        }              
        
        $("form[name='EquidadIn34Form'] input[name='tv_p3,"+i+"']").val(parseFloat($("form[name='EquidadIn34Form'] input[name='tv_p1,"+i+"']").val())+parseFloat($("form[name='EquidadIn34Form'] input[name='tv_p2,"+i+"']").val()));   
        if(parseInt($("form[name='EquidadIn34Form'] input[name='tv_tot_re_ing,"+i+"']").val())>0){
            $("form[name='EquidadIn34Form'] input[name='tv_p4,"+i+"']").val(getDecimal(parseFloat($("form[name='EquidadIn34Form'] input[name='tv_re_ing_h,"+i+"']").val()/$("form[name='EquidadIn34Form'] input[name='tv_tot_re_ing,"+i+"']").val()*100)));
        }else{
            $("form[name='EquidadIn34Form'] input[name='tv_p4,"+i+"']").val(0);
        }  
        
        if(parseInt($("form[name='EquidadIn34Form'] input[name='tv_tot_re_ing,"+i+"']").val())>0){
            $("form[name='EquidadIn34Form'] input[name='tv_p5,"+i+"']").val(getDecimal(parseFloat($("form[name='EquidadIn34Form'] input[name='tv_re_ing_m,"+i+"']").val()/$("form[name='EquidadIn34Form'] input[name='tv_tot_re_ing,"+i+"']").val()*100)));
        }else{
            $("form[name='EquidadIn34Form'] input[name='tv_p5,"+i+"']").val(0);
        }    
        
        $("form[name='EquidadIn34Form'] input[name='tv_p6,"+i+"']").val(parseFloat($("form[name='EquidadIn34Form'] input[name='tv_p4,"+i+"']").val())+parseFloat($("form[name='EquidadIn34Form'] input[name='tv_p5,"+i+"']").val()));
        if(parseInt($("form[name='EquidadIn34Form'] input[name='tv_total_h_m,"+i+"']").val())>0){
            $("form[name='EquidadIn34Form'] input[name='tv_p7,"+i+"']").val(getDecimal(parseFloat($("form[name='EquidadIn34Form'] input[name='tv_tot_h,"+i+"']").val()/$("form[name='EquidadIn34Form'] input[name='tv_total_h_m,"+i+"']").val()*100)));
        }else{
            $("form[name='EquidadIn34Form'] input[name='tv_p7,"+i+"']").val(0);
        }  
       
        if($("form[name='EquidadIn34Form'] input[name='tv_total_h_m,"+i+"']").val()>0){
            $("form[name='EquidadIn34Form'] input[name='tv_p8,"+i+"']").val(getDecimal(parseFloat($("form[name='EquidadIn34Form'] input[name='tv_tot_m,"+i+"']").val()/$("form[name='EquidadIn34Form'] input[name='tv_total_h_m,"+i+"']").val()*100)));
        }else{
            $("form[name='EquidadIn34Form'] input[name='tv_p8,"+i+"']").val(0);           
        }
        $("form[name='EquidadIn34Form'] input[name='tv_p9,"+i+"']").val(parseFloat($("form[name='EquidadIn34Form'] input[name='tv_p7,"+i+"']").val())+parseFloat($("form[name='EquidadIn34Form'] input[name='tv_p8,"+i+"']").val()));
    }
    $("form[name='EquidadIn34Form'] input[name='tva_1']").val(tv_ing_h),$("form[name='EquidadIn34Form'] input[name='tva_2']").val(tv_ing_m),
    $("form[name='EquidadIn34Form'] input[name='tva_3']").val(tv_tot_ing),$("form[name='EquidadIn34Form'] input[name='tva_4']").val(tv_re_ing_h),
    $("form[name='EquidadIn34Form'] input[name='tva_5']").val(tv_re_ing_m), $("form[name='EquidadIn34Form'] input[name='tva_6']").val(tv_tot_re_ing),
    $("form[name='EquidadIn34Form'] input[name='tva_7']").val(tv_tot_h),$("form[name='EquidadIn34Form'] input[name='tva_8']").val(tv_tot_m),
    $("form[name='EquidadIn34Form'] input[name='tva_9']").val(tv_total_h_m);    
    if(parseInt($("form[name='EquidadIn34Form'] input[name='tva_8']").val())>0){
        $("form[name='EquidadIn34Form'] input[name='tt_p']").val(getDecimal(parseFloat($("form[name='EquidadIn34Form'] input[name='tva_7']").val()/$("form[name='EquidadIn34Form'] input[name='tva_8']").val()*100)) + " %");
    }else{
        $("form[name='EquidadIn34Form'] input[name='tt_p']").val(0);
    }
}

function recuperarValores34(){    
    var num_niv=$("form[name='EquidadIn34Form'] input[name='niveles']").val(),cadena="-";
    for(var i=1;i<=num_niv;i++){
        var num_pro=$("form[name='EquidadIn34Form'] input[name='num_pro"+i+"']").val()
        for(var j=1;j<=num_pro;j++){               
            cadena=cadena.concat($("form[name='EquidadIn34Form'] input[id='ing_h,"+i+","+j+"']").attr("Name"),",",$("form[name='EquidadIn34Form'] input[id='ing_h,"+i+","+j+"']").val(),",",
                $("form[name='EquidadIn34Form'] input[id='ing_m,"+i+","+j+"']").val(),",",$("form[name='EquidadIn34Form'] input[id='re_ing_h,"+i+","+j+"']").val(),",",
                $("form[name='EquidadIn34Form'] input[id='re_ing_m,"+i+","+j+"']").val(),"-");                       
        }
    }
    return cadena;
}

function validarGuardado34(){
    var num_niv=$("form[name='EquidadIn34Form'] input[name='niveles']").val(),cadena="";
    for(var i=1;i<num_niv;i++){
        if(parseInt($("form[name='EquidadIn34Form'] input[id='tv_total_h_m,"+i+"']").val())!=parseInt($("form[name='EquidadIn34Form'] input[id='niv_mat,"+i+"']").val())){
            $("form[name='EquidadIn34Form'] input[id='tv_total_h_m,"+i+"']").removeClass("inputok").addClass("inputalert");
            $().alerta('amarillo', 'La matricula total del nivel "'+$("form[name='EquidadIn34Form'] input[id='niv_mat,"+i+"']").attr("Name") +'" en el cuadro 34.1 no coincide con la matricula "'+$("form[name='EquidadIn34Form'] input[id='niv_mat,"+i+"']").val()+'" capurada anteriormete . Por favor verifique los datos.');           
            cadena="Error";    
        }else{
            $("form[name='EquidadIn34Form'] input[id='tv_total_h_m,"+i+"']").removeClass("inputalert").addClass("inputok");           
        }
    }     
    if($('form[name="EquidadIn34Form"] input').hasClass('inputerror') || $('form[name="EquidadIn34Form"] input').hasClass('inputalert')){
        //$().alerta('rojo','Los datos no han sido guardados.');
        cadena="Error";    
    } else {
        // $().alerta('verde', 'Datos guardados correctamente.');
        cadena=recuperarValores34();
        //SE GUARDARA LOS COMENTARIOS
           var comentario = $("form[name='EquidadIn34Form'] input[name='comentario']").val();
            if(comentario != "Sin comentarios"){
                var indicador = "34";
                GuardarComentarios(comentario, indicador);
            }
    }
    return cadena;
}
function blurVal34(){
    $('form[name="EquidadIn34Form"] input[id^="ing_h,"]').each(function(index, domEle) {
        $(domEle).blur();
    });
}
