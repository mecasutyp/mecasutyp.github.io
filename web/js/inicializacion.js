
function InicializarIndicador(ind){
    switch (ind) {
        case 'EficaciaIn1Form':
            retrieveURL('/EficaciaIn1.msut?ask=consultar', 'EficaciaIn1Form'); 
            $("[id^=radio]").buttonset();
            operacionesInd1();
            checkEficaciaIn1();
            break;

        case 'EficaciaIn2Form':
        operacionesEficaciaIn2(1);
            break;

        case 'EficaciaIn3Form':
            obtenervalores3();
            break;

        case 'EficaciaIn4Form':
            blurVal4();
            break;

        case 'EficaciaIn5Form':
            cargarPes();
            break;

        case 'EficaciaIn6Form':
            obtenervalores6();
            break;

        case 'EficaciaIn7Form':
            blurVal7();
            break;

        case 'EficaciaIn8Form':
            $("[id^=radio]").buttonset();
            checkEficaciaIn8();
            break;

        case 'EficaciaIn9Form':
            operacionesEficaciaIn9();
            break;

        case 'EficaciaIn10Form':
            $("[id^=radio]").buttonset();
            checkEficaciaIn10(1) ;
            blurVal10();
            break;

        case 'EficaciaIn11Form':
            operacionesEficaciaIn11();
            break;

        case 'EficienciaIn11Form':
            operacionesInd12();
            break;

        case 'EficienciaIn12Form':
            operacionesInd12();
            break;

        case 'EficienciaIn13Form':
            operacionesEficienciaIn13();
            turnoEficienciaIn13();
            break;

        case 'EficienciaIn14Form':
            operacionesInd14();
            break;

        case 'EficienciaIn15Form':
            $( "[id^='radio']" ).buttonset();
            $( "#inicio1").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                    $( "#fin1").datepicker( "option", "minDate", selectedDate );
                    $( "#prox1").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });
            $( "#fin1").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                    $( "#inicio1").datepicker( "option", "maxDate", selectedDate );
                    //$( "#prox1").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });
            $( "#prox1").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                   // $( "#inicio1").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });

            $( "#inicio2").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                    $( "#fin2").datepicker( "option", "minDate", selectedDate );
                    $( "#prox2").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });
            $( "#fin2").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                    $( "#inicio2").datepicker( "option", "maxDate", selectedDate );
                    // $( "#prox2").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });
            $( "#prox2").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                  //  $( "#inicio2").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });

            $( "#inicio3").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                    $( "#fin3").datepicker( "option", "minDate", selectedDate );
                    $( "#prox3").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });
            $( "#fin3").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                    $( "#inicio3").datepicker( "option", "maxDate", selectedDate );
                    //  $( "#prox3").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });
            $( "#prox3").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                  //  $( "#inicio3").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });

            $( "#inicio4").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                    $( "#fin4").datepicker( "option", "minDate", selectedDate );
                    $( "#prox4").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });
            $( "#fin4").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                    $( "#inicio4").datepicker( "option", "maxDate", selectedDate );
                    // $( "#prox4").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });
            $( "#prox4").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                  //  $( "#inicio4").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });

            $( "#inicio5").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                    $( "#fin5").datepicker( "option", "minDate", selectedDate );
                    $( "#prox5").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });
            $( "#fin5").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
                    $( "#inicio5").datepicker( "option", "maxDate", selectedDate );
                    // $( "#prox5").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });
            $( "#prox5").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd",
                onSelect: function( selectedDate ) {
               //     $( "#inicio5").datepicker( "option", "minDate", selectedDate );
                    Eficiencia15Radios1();
                }
            });          
            Eficiencia15Radios1();
            break;
        case 'EficienciaIn16Form':
            eficienciaIn16Operaciones();
            break;

        case 'EficienciaIn17Form':
            operacionesEficienciaIn17();
            break;

        case 'PertinenciaIn18Form':
            $("[id^='rad']").buttonset();
            //$("[id^='opt']").buttonset();
            //operacionesPertinenciaG18();
            blurVal18();           
            $(  "[id^=vigencia_evaluacion]" ).datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd"
            });
            $(  "[id^=fecha_evaluacion]" ).datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd"
            });
            $(  "[id^=vigencia_evaluacionb]" ).datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd"
            });
            $(  "[id^=fecha_evaluacionb]" ).datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd"
            });
            break;

        case 'PertinenciaIn19Form':
            $("[id^='radio1']").buttonset();
            $("[id^='radio2']").buttonset();            
           // blurVal19();
           operacionesPertinencia19(1);
           // operacionesPertinencia19b();
            $(  "[id^=fecha_acreditacion]" ).datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd"
            });
            $(  "[id^=vigencia_acreditacion]" ).datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd"
            });
            $(  "[id^=fecha_acreditacionb]" ).datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd"
            });
            $(  "[id^=vigencia_acreditacionb]" ).datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat:"yy-mm-dd"
            });
            break;

        case 'PertinenciaIn20Form':
            if($("form[name='PertinenciaIn20Form'] input[name='val_19']").val()==1){
                $("form[name='PertinenciaIn20Form'] input[name='val_19']").prop("checked", true);
            }
            if($("form[name='PertinenciaIn20Form'] input[name='val_20']").val()==1){
                $("form[name='PertinenciaIn20Form'] input[name='val_20']").prop("checked", true);
                
                
            }
            if($("form[name='PertinenciaIn20Form'] input[name='val_21']").val()==1){
                $("form[name='PertinenciaIn20Form'] input[name='val_21']").prop("checked", true);
                
                
            }
            if($("form[name='PertinenciaIn20Form'] input[name='val_22']").val()==1){
                $("form[name='PertinenciaIn20Form'] input[name='val_22']").prop("checked", true);
                $("form[name='PertinenciaIn20Form'] input[name='val_23']").removeAttr("disabled");   
                
            }
            $( "#check" ).buttonset();
            operacionesPertinencia20(1,6,1);
            break;

        case 'PertinenciaIn21Form':
            //alert($("#pert21 > [id^='contenedorServicio_']").attr("name"));
            //alert($("#pert21 :first-child").attr("name"));
            //alert($("#pert21 :first-child").attr("id"));
            cargarCuerpo21($("#pert21 :first-child").attr("name"), $("#pert21 :first-child").attr("id"), true);
            //blurVal21();
            break;
        case 'PertinenciaIn22Form':
            
           // $("[id^='rad']").buttonset();
            operacionesPertinencia22a(1);
            operacionesPertinencia22a(2);
            operacionesPertinencia22a(3);
            operacionesPertinencia22a(4);

           // operacionesblur22();
            break;

        case 'PertinenciaIn23Form':
            operacionesIndicador23();
            break;

        case 'PertinenciaIn24Form':
            operacionesPertinenciaIn24(),
            operaciones2PertinenciaIn24();
            break;

        case 'PertinenciaIn25Form':
            operacionesPertinenciaIn25();
            break;

        case 'VinculacionIn26Form':
            cargarDatos26();
            break;

        case 'VinculacionIn27Form':
            cargarDatos27();
            break;

        case 'VinculacionIn28Form':
            blurVal28();
            break;

        case 'VinculacionIn29Form':
            blurVal29();
            cargarDatos29();
            break;

        case 'VinculacionIn30Form':
            operacionesVinculacion30();
            break;

        case 'VinculacionIn31Form':
            $("[id^=radio]").buttonset();
            $("input[type='radio'][id^=radio]").click(ind31aplica).click();
            blurVal21();
            break;

        case 'VinculacionIn32Form':
            operaciones_32();
            break;
            
        case 'EquidadIn33Form':
            operacionesIndicador33();
            break;

        case 'EquidadIn34Form':
            blurVal34();
            break;

        case 'EquidadIn35Form':
            operacionesIndicador35();
            break;

        case 'EquidadIn36Form':
            cargarDatos36();
            break;
    }
}