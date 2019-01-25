//<%@ page contentType="text/html;charset=UTF-8" %>
jQuery(function($) {
  var fields = [
    {
      type: 'autocomplete',
      label: '自动完成',
      required: true,
      values: [
        {label: 'SQL'},
        {label: 'C#'},
        {label: 'JavaScript'},
        {label: 'Java'},
        {label: 'Python'},
        {label: 'C++'},
        {label: 'PHP'},
        {label: 'Swift'},
        {label: 'Ruby'}
      ]
    },
    {
      label: '评分',
      attrs: {
        type: 'starRating'
      },
      icon: '🌟'
    }
      ,
      {
          label: '部门选择',
          attrs: {
              type: 'officeSelect'
          },
          icon: '👨'
      }
      ,
      {
          label: '人员选择',
          attrs: {
              type: 'userSelect'
          },
          icon: '👨'
      } ,
      {
          label: '省/市/区',
          attrs: {
              type: 'cityPicker'
          },
          icon: '👨'
      }
  ];

  var replaceFields = [
    {
      type: 'textarea',
      subtype: 'tinymce',
      label: '富文本',
      required: true,
    }
  ];


  var templates = {
    starRating: function(fieldData) {
        var rate = isNaN(parseFloat(fieldData.value))? 0:parseFloat(fieldData.value);
      return {
        field: '<div id="'+fieldData.name+'" ></div id="'+fieldData.name+'" ><input type="hidden"  name="'+fieldData.name+'" value="'+rate+'"/> <div  id="'+fieldData.name+'counter'+'"  class="counter">'+rate+'</div>',
        onRender: function() {
            $(document.getElementById(fieldData.name)).rateYo({rating: rate});
            $("#"+fieldData.name).rateYo()
                .on("rateyo.change", function (e, data) {

                    var rating = data.rating;
                    $("#"+fieldData.name+'counter').text(rating);
                });
            $("#"+fieldData.name).rateYo()
                .on("rateyo.set", function (e, data) {
                    $("input[name='"+fieldData.name+"'").val(data.rating);
                });


        }
      };
    },
      officeSelect: function(fieldData) {
          var office = fieldData.name;
          var required = "";
          var value = "";
          var labelValue = "" ;
          if(fieldData.labelValue){
              labelValue = fieldData.labelValue;
          }
          if(fieldData.values){
              value = fieldData.value;
          }
          if(fieldData.required)
              required = "required";
          return {
              field: '<input id="'+office+'Id" name="'+office+'" class="form-control '+required+' " type="hidden" value="'+value+'" aria-required="true">' +
              '<div class="input-group" style="width:100%">' +
              '<input id="'+office+'Name" readonly="readonly" type="text" value="'+labelValue+'" data-msg-required="" class="form-control  '+required+' " style="" aria-required="true">' +
              '<span class="input-group-btn"><button type="button" id="'+office+'Button" class="btn   btn-primary  "><i class="fa fa-search"></i> </button> ' +
              '<button type="button" id="'+office+'DelButton" class="close" data-dismiss="alert" style="position: absolute; top: 5px; right: 53px; z-index: 999; display: block;">×</button></span> </div>' +
              '<label id="'+office+'Name-error" class="error" for="'+office+'Name" style="display:none"></label>',
              onRender: function() {
                  $("#"+office+"Button, #"+office+"Name").click(function(){
                      // 是否限制选择，如果限制，设置为disabled
                      if ($("#"+office+"Button").hasClass("disabled")){
                          return true;
                      }
                      // 正常打开
                      top.layer.open({
                          type: 2,
                          area: ['300px', '420px'],
                          title:"选择部门",
                          ajaxData:{selectIds: $("#"+office+"Id").val()},
                          content: ctx+ "/tag/treeselect?url="+encodeURIComponent("/sys/office/treeData?type=2")+"&module=&checked=&extId=&isAll=&allowSearch=" ,
                          btn: ['确定', '关闭']
                          ,yes: function(index, layero){ //或者使用btn1
                              var tree = layero.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
                              var ids = [], names = [], nodes = [];
                              if ("" == "true"){
                                  nodes = tree.get_checked(true);
                              }else{
                                  nodes = tree.get_selected(true);
                              }
                              for(var i=0; i<nodes.length; i++) {//
                                  if (nodes[i].original.isParent){
                                      top.layer.msg("不能选择父节点（"+nodes[i].text+"）请重新选择。", {icon: 0});
                                      return false;
                                  }//
                                  ids.push(nodes[i].id);
                                  names.push(nodes[i].text);//
                                  break; // 如果为非复选框选择，则返回第一个选择
                              }
                              $("#"+office+"Id").val(ids.join(",").replace(/u_/ig,""));
                              $("#"+office+"Name").val(names.join(","));
                              $("#"+office+"Name").focus();
                              top.layer.close(index);
                          },
                          cancel: function(index){ //或者使用btn2
                              //按钮【按钮二】的回调
                          }
                      });

                  });

                  $("#"+office+"DelButton").click(function(){
                      // 是否限制选择，如果限制，设置为disabled
                      if ($("#"+office+"Button").hasClass("disabled")){
                          return true;
                      }
                      // 清除
                      $("#"+office+"Id").val("");
                      $("#"+office+"Name").val("");
                      $("#"+office+"Name").focus();

                  });
              }
          };
      },

      userSelect: function(fieldData) {
          var user = fieldData.name;
          var value = "";
          var labelValue = "" ;
          if(fieldData.labelValue){
              labelValue = fieldData.labelValue;
          }
          if(fieldData.values){
              value = fieldData.value;
          }
          var required = "";
          if(fieldData.required)
              required = "required";
          return {
              field: '	<input id="'+user+'Id" name="'+user+'" class="form-control '+required+' " type="hidden" value="'+value+'"  aria-required="true"><div class="input-group" style="width:100%">' +
              '<input id="'+user+'Name"  readonly="readonly" type="text" value="'+labelValue+'" data-msg-required="" class="form-control  '+required+' " style="" aria-required="true">' +
              ' <span class="input-group-btn"><button type="button" id="'+user+'Button" class="btn   btn-primary  "><i class="fa fa-search"></i> </button><button type="button" id="'+user+'DelButton" class="close" data-dismiss="alert" style="position: absolute; top: 5px; right: 53px; z-index: 999; display: block;">×</button></span> </div><label id="'+user+'Name-error" class="error" for="'+user+'Name" style="display:none"></label>',
              onRender: function() {
                  $("#"+user+"Button, #"+user+"Name").click(function(){
                      // 是否限制选择，如果限制，设置为disabled
                      if ($("#"+user+"Button").hasClass("disabled")){
                          return true;
                      }
                      // 正常打开

                      jp.openUserSelectDialog(false,function(ids, names){
                          $("#"+user+"Id").val(ids.replace(/u_/ig,""));
                          $("#"+user+"Name").val(names);
                          $("#"+user+"Name").focus();
                      })

                  });

                  $("#"+user+"DelButton").click(function(){
                      // 是否限制选择，如果限制，设置为disabled
                      if ($("#"+user+"Button").hasClass("disabled")){
                          return true;
                      }
                      // 清除
                      $("#"+user+"Id").val("");
                      $("#"+user+"Name").val("");
                      $("#"+user+"Name").focus();

                  });
              }
          };
      },
      cityPicker: function(fieldData) {
          var value = "";
          if(fieldData.value){
              value = fieldData.value;
          }

          return {
              field: '<input name="'+fieldData.name+'" value="'+value+'" id="'+fieldData.name+'" data-toggle="city-picker" >',
              onRender: function() {
                  $("#"+fieldData.name).citypicker();
              }
          };
      }

  };

  var inputSets = [{
        label: 'User Details',
        icon: '👨',
        name: 'user-details', // optional
        showHeader: true, // optional
        fields: [{
          type: 'text',
          label: 'First Name',
          className: 'form-control'
        }, {
          type: 'select',
          label: 'Profession',
          className: 'form-control',
          values: [{
            label: 'Street Sweeper',
            value: 'option-2',
            selected: false
          }, {
            label: 'Brain Surgeon',
            value: 'option-3',
            selected: false
          }]
        }, {
          type: 'textarea',
          label: 'Short Bio:',
          className: 'form-control'
        }]
      }, {
        label: 'User Agreement',
        fields: [{
          type: 'header',
          subtype: 'h3',
          label: 'Terms & Conditions',
          className: 'header'
        }, {
          type: 'paragraph',
          label: 'Leverage agile frameworks to provide a robust synopsis for high level overviews. Iterative approaches to corporate strategy foster collaborative thinking to further the overall value proposition. Organically grow the holistic world view of disruptive innovation via workplace diversity and empowerment.',
        }, {
          type: 'paragraph',
          label: 'Bring to the table win-win survival strategies to ensure proactive domination. At the end of the day, going forward, a new normal that has evolved from generation X is on the runway heading towards a streamlined cloud solution. User generated content in real-time will have multiple touchpoints for offshoring.',
        }, {
          type: 'checkbox',
          label: 'Do you agree to the terms and conditions?',
        }]
      }];

  var typeUserDisabledAttrs = {
    autocomplete: ['access']
  };


  // test disabledAttrs
  var disabledAttrs = ['placeholder'];

    toggleEdit();
    var formData = jp.unescapeHTML('${formData}');

    $('.render-wrap').formRender({
        formData: formData,
        templates: templates
    });

  var fbOptions = {


     i18n: {
          locale: 'zh-CN'
    },
    subtypes: {
      text: ['datetime-local']
    },

    stickyControls: {
      enable: true
    },
    sortableControls: true,
    fields: fields,
    templates: templates,
    inputSets: inputSets,
    typeUserDisabledAttrs: typeUserDisabledAttrs,
    disableInjectedStyle: false,
    disableFields: ['autocomplete'],
    replaceFields: replaceFields,
    disabledFieldButtons: {
      text: ['copy']
    }
    // controlPosition: 'left'
    // disabledAttrs
  };
  var editing = true;

  /**
   * Toggles the edit mode for the demo
   * @return {Boolean} editMode
   */
  function toggleEdit() {
    document.body.classList.toggle('form-rendered', editing);
    return editing = !editing;
  }


  // jp.ajaxForm("#inputForm",function (data) {
  //     if(data.success){
  //         jp.toastr.success(data.msg);
  //        jp.go('${ctx}/form/dynamic/list?form_id=${form_id}');
  //     }
  //
  //
  // })

});



