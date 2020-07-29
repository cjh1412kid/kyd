var atree;

var avm = new Vue({
  el: '#templateLayer',
  data: {
    tmpNodeSeq: null,
    tmpNode: null,
    rMenuTitle: null,
    name:'',
  }
});

var aprompt = new Vue({
  el: '#prompt',
  data: {
    templateName: null,
    promptTip: "",
  }
});
<!-- 实例化编辑器 -->
var ue = UE.getEditor('container', {
  enterTag: '',
  allowDivTransToP: false
});



//存储右击目标对象
var clickTarget = null;

ue.ready(function () {
  /*隐藏右上角本地保存提示框*/
  $(".edui-editor-messageholder.edui-default").css({"visibility": "hidden"});
  /*加载设为默认的模版为选中状态，编辑器显示内容*/
  if (token != null) {
    $.get(baseURL + '/system/orderAgreement/getAgreementByUser', function (data, status) {
    	
      if (data.result) {
        if (data.code == 0) {
          if (data.result.length > 1) {
              var content = decodeURIComponent(encodeSearchKey(data.result));
              setName(data.name)
              ue.setContent(content);
              vm.seq=data.seq
              setTimeout(function () {
                setChildNodesDraggable();
              }, 500);
          }
        } else {
          alert(data.msg);
        }
      } else {
        //无返回结果清空编辑器内容
        clearContent();
      }

    })
  } else {
    // console.log("token:", token)
  }

  ue.body.onmousedown = function (e) {
    var e = e || window.event;
    //获取右击事件目标对象
    if (e.button == 2) {
      clickTarget = e.target;
      // console.log(clickTarget);
    }
  }

  setEditorBodyOnDrag();
});


/*自定义请求地址*/
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
UE.Editor.prototype.getActionUrl = function (action) {
  if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
    return baseURL + '/system/orderAgreement/imgUpload?token=' + token + '&action=' + action;
  } else if (action == 'uploadvideo') {
    return baseURL + '/system/orderAgreement/videoUpload?token=' + token + '&action=' + action;
  } else {
    return this._bkGetActionUrl.call(this, action);
  }
}


/*获取编辑器内容生成网页*/
function getAllHtml() {
  console.log(ue.getAllHtml())
}

/*获取编辑器内容*/
function getContent2() {
  // var res=ue.body.innerHTML;
  // console.log(res,typeof res)
  console.log(ue.getContent());
}

/*将获取到的内容编译后显示在编辑器*/
function setContent2(content) {
  ue.setContent(decodeURIComponent(content));
  setTimeout(function () {
    setChildNodesDraggable();
  }, 500);
}

/*获取编辑器内容并上传*/
function getContentAndUpload() {
	console.log(ue.getContent())
  var agreementContent = ue.getContent();
	var name=avm.name;
  /*没有节点选中，将创建新节点*/
  if (!vm.seq) {

    if (!agreementContent || agreementContent.trim() == "") {
      alert("内容不能为空！")
      return;
    }
    agreementContent = encodeURIComponent(agreementContent);
    if(!name || name.trim() == ""){
    	
  
    /*设置输入框提示信息*/
    aprompt.promptTip = "请输入新模板名称";

    layer.open({
      type: 1,
      offset: '50px',
      skin: 'layui-layer-molv',
      title: "请输入新合同名称",
      area: ['300px', '150px'],
      shade: 0,
      shadeClose: false,
      content: jQuery("#prompt"),
      btn: ['确定', '取消'],
      btn1: function (index) {
        var name = aprompt.templateName;
        if (name != null && name.trim() != "" && agreementContent != null && agreementContent.trim() != "") {
          $.post(baseURL + "system/orderAgreement/save",
            {
        	  agreementContent: agreementContent,
              name: name
            },
            function (data, status) {
              if (data.code == 0) {
                alert("新合同创建成功");
                
              } else {
                alert("合同创建失败")
              }
              aprompt.templateName = null;
            })
        } else {
          alert("模版名称和内容不能为空");
        }


        layer.close(index);
      }
    });
    }

  } else {  
	  
	  if (name != null && name.trim() != "" && agreementContent != null && agreementContent.trim() != "") {
		    $.post(baseURL + "system/orderAgreement/update",
		      {
		        seq:vm.seq,
		        agreementContent: agreementContent,
		        name: name
		      },
		      function (data, status) {
		        if (data.code == 0) {
		          alert("修改成功");
		        } else {
		          alert("修改失败")
		        }
		      })
		      }else {
		          alert("合同名称和内容不能为空");
		      }

}
}
function setName(name){
	avm.name=name;
	vm.agreement.name=name;
}
/*清空内编辑器内容*/
function clearContent() {
  ue.setContent("");
}

function createNewTemplate() {
  clearContent();
}

/*  表格功能区   */

var vm;
vm = new Vue({
	  el: '#myModal',
	  data: {
	    showList: true,
	    title: '',
	    agreement:{
	    	name:'',
	    },
	    seq:'',
	  }, 
})
var categorySetting = {
  data: {
    simpleData: {
      enable: true,
      idKey: "seq",
      pIdKey: "parentSeq",
      rootPId: -1
    },
    key: {
      url: "nourl"
    }
  },
  check: {
    enable: true,
    nocheckInherit: true
  }
};
/*ueditor编辑器工具栏--自定义按钮--功能区*/

/*定义图片排列列数，对所有图片，单列*/
ue.commands['imgonecols'] = {

  execCommand: function () {

    var imgs = this.document.getElementsByTagName("img");

    for (var i = 0, img; img = imgs[i++];) {
      // $(img).attr("style", "float:left");
      $(img).attr("width", "100%");
      // $(img).removeAttr("_src");
    }
  }
}
/*定义图片排列列数，对所有图片，双列*/
ue.commands['imgtwocols'] = {

  execCommand: function () {

    var imgs = this.document.getElementsByTagName("img");

    for (var i = 0, img; img = imgs[i++];) {
      // $(img).attr("style", "float:left");
      $(img).attr("width", "50%");
      // $(img).removeAttr("_src");
    }
  }
}
/*定义图片排列列数，对所有图片，三列*/
ue.commands['imgthreecols'] = {

  execCommand: function () {

    var imgs = this.document.getElementsByTagName("img");

    for (var i = 0, img; img = imgs[i++];) {
      // $(img).attr("style", "float:left");
      $(img).attr("width", "33.33%");
      // $(img).removeAttr("_src");
    }
  }
}

/*设置选中的显示宽度，100%*/
ue.commands['img100'] = {

  execCommand: function () {
    var focusNode = ue.selection.getStart();
    UE.dom.domUtils.setAttributes(focusNode, {width: '100%'})
  }
}
/*设置选中的显示宽度，50%*/
ue.commands['img50'] = {

  execCommand: function () {
    var focusNode = ue.selection.getStart();
    UE.dom.domUtils.setAttributes(focusNode, {width: '50%'})
  }
}
/*设置选中的显示宽度，33.33%*/
ue.commands['img33'] = {

  execCommand: function () {
    var focusNode = ue.selection.getStart();
    UE.dom.domUtils.setAttributes(focusNode, {width: '33.3333%'})
  }
}

ue.commands['imgscale'] = {

  execCommand: function (cmdName, width) {
    var focusNode = ue.selection.getStart();
    UE.dom.domUtils.setAttributes(focusNode, {width: width})
  }
}

/*自定义输入图片宽度比例*/
ue.commands['imgscale2'] = {

  execCommand: function () {

    var focusNode = ue.selection.getStart();

    /*设置输入框提示信息*/
    aprompt.promptTip = "请输入(1~100)";

    layer.open({
      type: 1,
      offset: '50px',
      skin: 'layui-layer-molv',
      title: "自定义图片宽度比例(1~100)%",
      area: ['300px', '150px'],
      shade: 0,
      shadeClose: false,
      content: jQuery("#prompt"),
      btn: ['确定', '取消'],
      btn1: function (index) {
        var num = aprompt.templateName;
        num = Number.parseInt(num);
        if (num > 0 && num <= 100) {

          UE.dom.domUtils.setAttributes(focusNode, {width: num + '%'});
        } else {
          alert("您输入的值不合法，请重新输入(1~100)")
        }

        aprompt.templateName = null;
        layer.close(index);
      }
    });
  }
}

/*定义右键删除选中图片菜单*/
ue.commands['rdelete'] = {

  execCommand: function () {
    var focusNode = ue.selection.getStart();
    if (focusNode && focusNode.tagName == "IMG") {
      UE.dom.domUtils.remove(focusNode);
    }
  }
}

/*定义右键菜单  删除所有视频对象*/
ue.commands['rdelvideos'] = {

  execCommand: function () {
    var videos = this.document.getElementsByTagName("video");
    for (var i = videos.length - 1; i >= 0; i--) {
      //移除video标签
      UE.dom.domUtils.remove(videos[i]);
    }
  }
}


/*右键菜单 删除右击对象*/
ue.commands['rdelvideo'] = {

  execCommand: function () {

    if (clickTarget && clickTarget.tagName == "VIDEO") {
      UE.dom.domUtils.remove(clickTarget);
    }
    clickTarget = null;
  }
}


/*定义右键视频缩略图菜单*/
var videos;
ue.commands['videoposter'] = {

  execCommand: function () {
    $('#videoposterModal').modal('toggle');
    videos = this.document.getElementsByTagName("video");
  }
};

/*定义右键菜单 -- 光标跳到文档最后*/
ue.commands['insertendrow'] = {

  execCommand: function () {
    ue.focus(true);
  }
};

var focusNode2;
/*定义链接商品按钮功能*/
ue.commands['newlink'] = {

  execCommand: function () {
    focusNode2 = ue.selection.getStart();
    if (focusNode2.tagName != 'IMG') {
      return;
    }
    var linkType = focusNode2.getAttribute("data-link-type");
    var linkSeq = focusNode2.getAttribute("data-link-seq");
    if (linkType != null && linkSeq != null && linkSeq != "") {
      vm.sowingMap.type = linkType;
      vm.sowingMap.linkSeq = linkSeq;
      if (linkType == 1) {
        vm.sowingMap.linkName = '已选择' + linkSeq.split(",").length + '商品';
      } else if (linkType == 2) {
        vm.sowingMap.linkName = '已选择' + linkSeq.split(",").length + '分类';
      } else if (linkType == 3) {

      }

    } else {
      vm.sowingMap.type = 1;
      vm.sowingMap.linkSeq = "";
      vm.sowingMap.linkName = '请点击关联内容'
    }

    var img = focusNode2.outerHTML;
    $('#imgShow').html(img);
    $('#myModal').modal('toggle');
  }
}

/*模态框提交按钮触发*/
function ok() {
  UE.dom.domUtils.setAttributes(focusNode2, {
    'data-link-type': vm.sowingMap.type,
    'data-link-seq': vm.sowingMap.linkSeq,
    'tapmode': "active",
    'onclick': 'openAllShoesList(this)',
    'title': vm.sowingMap.title
  })
  $('#myModal').modal('hide');
}


/*设置视频缩略图模态框提交按钮触发方法*/
function ok2() {
  var files = $("#inputfile").prop('files');//获取到文件列表

  if (files.length < 1) {
    alert("上传文件数量不能为0")
    return;
  }

  var formFile = new FormData();
  formFile.append("file", files[0]); //加入文件对象

  $.ajax({
    url: baseURL + "/system/orderAgreement/uploadVideoPoster",
    data: formFile,
    type: "Post",
    dataType: "json",
    cache: false,//上传文件无需缓存
    processData: false,//用于对data参数进行序列化处理 这里必须false
    contentType: false, //必须
    success: function (data, status) {
      if (data.code == 0) {
        for (var i = 0; i < videos.length; i++) {
          UE.dom.domUtils.setAttributes(videos[i], {poster: data.imgPath});
        }
        alert("视频缩略图设置成功");
      } else {
        alert("视频缩略图设置失败，请重试");
      }


    },
    error: function (data, status) {
      alert("图片上传失败");
    }
  });

  $('#videoposterModal').modal('hide');
}


$("#inputfile").change(function () {
  var file = this.files[0];

  //图片验证
  if (!/image\/\w+/.test(file.type)) {
    alert("文件必须为图片！");
    return;
  }

  if (window.FileReader) {
    var reader = new FileReader();
    reader.readAsDataURL(file);
    //监听文件读取结束后事件
    reader.onloadend = function (e) {
      //e.target.result就是最后的路径地址
      $("#videoposter").attr("src", e.target.result);
    };
  }
});

/*右键菜单功能   移动右键对象到下一个元素后*/
ue.commands['movedown'] = {

  execCommand: function () {

    if (clickTarget && (clickTarget.tagName == "IMG" || clickTarget.tagName == "VIDEO")) {
      var nextNode = UE.dom.domUtils.getNextDomNode(clickTarget);
      if (nextNode) {
        UE.dom.domUtils.insertAfter(nextNode, clickTarget);
      }
    }
  }
}

/*右键菜单功能   移动右键对象到上一个元素前*/
ue.commands['moveup'] = {

  execCommand: function () {

    if (clickTarget && (clickTarget.tagName == "IMG" || clickTarget.tagName == "VIDEO")) {
      var prevNode = clickTarget.previousSibling;
      if (prevNode) {
        UE.dom.domUtils.insertAfter(clickTarget, prevNode);
      }
    }
  }
}

/*加载模板树数据*/
function loadUeditorRecords(callback) {
  $.get(baseURL + '/system/orderAgreement/getUeditorRecords?token=' + token, function (r) {
    var records = r.records;

    for (var i = 0; i < records.length; i++) {
      records[i].parentSeq = 0;
    }
    records.push({seq: 0, pIdKey: -1, name: "所有模版"});
    // console.log(records, typeof records);
    atree = $.fn.zTree.init($("#templateTree"), templateTreeSetting, records);

    atree.expandAll(true);

    if (typeof (callback) === "function") {
      callback();
    }
  })

}
/*loadUeditorRecords回调函数*/
function checkedNode2() {
  setContent2(node.content);
}
/*拖动对象*/
var srcTarget;
/*存储目的对象宽度*/
var tmpWidth;

/**
 * 设置editor的body内元素的拖拽监听事件,编辑器初始化完成后执行
 */
function setEditorBodyOnDrag() {

  ue.body.ondragstart = function (e) {
    var fromTarget = e.target;
    if (fromTarget && (fromTarget.tagName == "IMG" || fromTarget.tagName == "VIDEO")) {
      srcTarget = fromTarget;
      // console.log("from : ", srcTarget);
    }
  }

  ue.body.ondragover = function (e) {
    e.preventDefault();
  }

  ue.body.ondragenter = function (e) {
    e.preventDefault();
    var tmpTarget = e.target;
    if (tmpTarget && !tmpTarget.isSameNode(srcTarget) && (tmpTarget.tagName == "IMG" || tmpTarget.tagName == "VIDEO")) {

      // console.log("enter:tmpTarget: ", tmpTarget)
      var _tmpTarget = $(tmpTarget);
      _tmpTarget.attr("style", "border-right: 2px dashed red");
      tmpWidth = _tmpTarget.attr("width");
      if (tmpWidth.charAt(tmpWidth.length - 1) == "%") {
        // console.log("------图片%---------------")
        var len = tmpWidth.substring(0, tmpWidth.length - 1) - 1;
        tmpTarget.setAttribute("width", len + "%");
      } else {
        var len = tmpWidth - 2;
        tmpTarget.setAttribute("width", len + "px");
      }
    }
  }

  ue.body.ondragleave = function (e) {
    e.preventDefault();
    var tmpTarget = e.target;
    if (tmpTarget && !tmpTarget.isSameNode(srcTarget) && (tmpTarget.tagName == "IMG" || tmpTarget.tagName == "VIDEO")) {
      tmpTarget.removeAttribute("style");
      tmpTarget.setAttribute("width", tmpWidth);
      // console.log("leave: ", tmpTarget)
    }
  }

  ue.body.ondrop = function (e) {
    e.preventDefault();
    // console.log("to : ", e.target)
    var toTarget = e.target;

    if (srcTarget && toTarget && !srcTarget.isSameNode(toTarget)
      && (toTarget.tagName == "IMG" || toTarget.tagName == "VIDEO")) {
      toTarget.removeAttribute("style");
      toTarget.setAttribute("width", tmpWidth);
      UE.dom.domUtils.insertAfter(toTarget, srcTarget);
    }
    /*    else {
          console.log("两个节点相同，不操作")
        }*/

    srcTarget = null;
    tmpWidth = null;
  }


}


/**
 * 给所有编辑器的内容元素添加draggable属性
 */
function setChildNodesDraggable() {

  var nodes = ue.body.childNodes;
  for (var i in nodes) {
    if (nodes[i].tagName == "IMG" || nodes[i].tagName == "VIDEO") {
      // console.log(nodes[i])
      nodes[i].setAttribute("draggable", "true");
    }
  }
}


function encodeSearchKey(key) {
    const encodeArr = [{
      code: '%',
      encode: '%25'
    }, {
      code: '?',
      encode: '%3F'
    }, {
      code: '#',
      encode: '%23'
    }, {
      code: '&',
      encode: '%26'
    }, {
      code: '=',
      encode: '%3D'
    }];
    return key.replace(/[%?#&=]/g, ($, index, str) => {
      for (const k of encodeArr) {
        if (k.code === $) {
          return k.encode;
        }
      }
    });
  }
