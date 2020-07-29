var vm = new Vue({
  el: '#rrapp',
  data: {
    /*创建模版*/
    template: {
      name: null,
      minSize: 35,
      maxSize: 41,
      details: []
    },
    /*聚焦最大最小码*/
    focus: {
      minSize: 35,
      maxSize: 41,
    },
    /*聚焦尺码数量输入框，暂存数量值*/
    focusSizeCount: 0,
    templateMap: {}
  },
  methods: {
    /*创建新的品类-配码模版*/
    createTemplate: function () {
      //模版名称不能为空
      if (isBlank(this.template.name)) {
        layer.msg("模版名称不能为空", {icon: 0})
        this.$refs.templateNameInput.focus();
        return;
      }

      //尺码范围检查
      if (!this.checkSizeBound()) {
        this.$set(this.template, 'maxSize', this.focus.maxSize);
        this.$set(this.template, 'minSize', this.focus.minSize);
        return;
      }

      if (!this.template.details || this.template.details.length == 0) {
        layer.msg("尺码模版内容为空", {icon: 2})
        return;
      }
      var min = this.template.details[0].size;
      var max = this.template.details[this.template.details.length - 1].size;

      // console.log('min:',min, this.template.minSize, ' max:',max,this.template.maxSize)
      if (min != this.template.minSize || max != this.template.maxSize) {
        layer.alert("尺码模版的尺码段和尺码段不相同，请检查", {icon: 0})
        return;
      }

      //尺码段相同模版名称不能重复
      var sizeKey = this.template.minSize + ' - ' + this.template.maxSize

      for (key in this.templateMap) {
        if (sizeKey == key) {
          var templates = this.templateMap[key];
          for (var i in templates) {
            if (templates[i].name == this.template.name) {
              layer.alert("尺码段：" + key + " 模版名称：" + this.template.name + " 已存在！不能重复", {icon: 0})
              return;
            }
          }
          break;
        }
      }


      //检查尺码数量值
      for (var i in this.template.details) {
        var detail = this.template.details[i];
        if (detail.per) {
          if (!isInteger(detail.per, "尺码" + detail.size)) {
            return;
          }
        } else {
          this.template.details[i].per = 0;
        }

      }

      //创建模版
      $.ajax({
        type: "POST",
        url: baseURL + 'system/sizeAllotTemplate/save',
        dataType: "json",
        cache: false,
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify(vm.template),
        success: function (data) {
          if (data.code == 0) {
            vm.reloadTemplateList();
          } else {
            layer.msg(data.msg)
          }
        }
      })

    },
    /*聚焦最大最小码输入值*/
    focusSizeBound: function () {
      this.focus = {
        minSize: this.template.minSize || 0,
        maxSize: this.template.maxSize || 0,
      }
      // console.log("暂存最大最小码：", this.focus.minSize, this.focus.maxSize)
    },
    /*聚焦尺码数量输入值*/
    focusSizeValue: function (index) {
      this.focusSizeCount = this.template.details[index].per;
    },
    /*监听尺码输入值改变*/
    changeSizeValue: function (event, size, index) {
      var changeVal = this.template.details[index].per;
      // console.log(changeVal, typeof changeVal)
      if (!isInteger(changeVal, "尺码" + size)) {
        this.$set(this.template.details[index], "per", this.focusSizeCount);
        event.target.focus();
      }
    },
    /*检查尺码范围是否合法*/
    checkSizeBound: function () {
      var minSize = this.template.minSize;
      var maxSize = this.template.maxSize;

      if (!minSize || !maxSize) {
        layer.msg("请先输入尺码范围")
        return false;
      } else if (isNaN(minSize) || isNaN(maxSize)) {
        layer.msg("尺码非数字，请检查")
        return false;
      } else if (minSize < 1 || maxSize < 1) {
        layer.msg("尺码不能小于1，请检查")
        return false;
      } else if (maxSize < minSize) {
        layer.msg("尺码范围不合法，请检查")
        return false;
      }

      return true;
    },
    /*改变尺码范围*/
    changeSizeBound: function () {
      //尺码范围检查
      if (!this.checkSizeBound()) {
        this.$set(this.template, 'maxSize', this.focus.maxSize);
        this.$set(this.template, 'minSize', this.focus.minSize);
      }

      var detailList = [];
      for (var i = this.template.minSize; i <= this.template.maxSize; i++) {
        detailList.push({size: i, per: null})
      }

      this.$set(this.template, 'details', detailList);
    },
    /*重加载配码模版数据*/
    reloadTemplateList: function () {

      $.get(baseURL + 'system/sizeAllotTemplate/list', function (data) {
        if (data.code == 0) {
          vm.templateMap = data.result;
        } else {
          layer.alert(data.msg)
        }
      })

    },
    deleteTemplate: function (templateSeq, templateName, index) {

      layer.confirm("确定要删除 [" + templateName + "] 吗", {icon: 3}, function (index) {

        $.get(baseURL + 'system/sizeAllotTemplate/delete/' + templateSeq, function (data) {
          if (data.code == 0) {
            vm.reloadTemplateList();
          } else {
            layer.alert(data.msg)
          }
        })

        layer.close(index);
      })
    }

  },
  created: function () {
    this.changeSizeBound();
    this.reloadTemplateList();
  }
})

/*判断是否是大于等于0的正整数*/
function isInteger(val, valName) {
  valName || (valName = '输入值');
  if (isNaN(val)) {
    layer.msg(valName + "必须为数字")
    return false;
  } else if (val < 0) {
    layer.msg(valName + "不能<0")
    return false;
  } else if (parseInt(val) != val) {
    layer.msg(valName + "必须为整数")
    return false;
  }
  return true;
}
