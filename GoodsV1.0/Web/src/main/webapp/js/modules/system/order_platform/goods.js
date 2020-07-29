//颜色尺码选择以及填库存
var stockItem = Vue.extend({
    name: 'stock-item',
    model: {
        prop: 'stockArray',
        event: 'stockDataChange'
    },
    data: function () {
        return {
            orderColorSelected: [],
            orderSizeSelected: [],
            orderPlatformSize: [],
            orderPlatformColor: [],
            orderPlatformSelectColor: '-1',
            orderPlatformSelectSize: '-1',
            setValueEvent: false
        }
    },
    methods: {
        orderPlatformAddColor: function () {
            var selectColorIndex = parseInt(this.orderPlatformSelectColor);
            if (selectColorIndex === -1) {
                alert("请选择一个颜色");
                return;
            }
            var indexColor = this.colorArray[selectColorIndex];
            for (var index = 0; index < this.orderColorSelected.length; index++) {
                if (this.orderColorSelected[index].seq == indexColor.seq) {
                    alert("颜色已选择！");
                    return;
                }
            }
            var selectColor = {};
            selectColor.seq = indexColor.seq;
            selectColor.name = indexColor.name;
            selectColor.code = indexColor.code;
            this.orderColorSelected.push(selectColor);
            this.orderPlatformSelectColor = '-1';
        },
        orderPlatformAddSize: function () {
            var selectSizeIndex = parseInt(this.orderPlatformSelectSize);
            if (selectSizeIndex === -1) {
                alert("请选择一个尺码");
                return;
            }
            var indexSize = this.sizeArray[selectSizeIndex];
            for (var index = 0; index < this.orderSizeSelected.length; index++) {
                if (this.orderSizeSelected[index].seq == indexSize.seq) {
                    alert("尺码已选择！");
                    return;
                }
            }
            this.orderSizeSelected.push(indexSize);
            this.orderPlatformSelectSize = '-1';
        },
        remarkChange: function () {
            this.manageOrderPlatformStockTable();
        },
        orderPlatformSizeCheck: function () {
            this.manageOrderPlatformStockTable();
        },
        orderPlatformColorCheck: function () {
            this.manageOrderPlatformStockTable();
        },
        manageOrderPlatformStockTable: function () {
            var newStockTableData = [];
            for (var index = 0; index < this.orderPlatformColor.length; index++) {
                var indexColor = this.orderPlatformColor[index];
                for (var subIndex = 0; subIndex < this.orderPlatformSize.length; subIndex++) {
                    var stockOne = {
                        seq: indexColor.seq,
                        code: indexColor.code,
                        name: indexColor.name,
                        length: this.orderPlatformSize.length,
                        sizeSeq: this.orderPlatformSize[subIndex].seq,
                        sizeCode: this.orderPlatformSize[subIndex].sizeCode,
                        sizeName: this.orderPlatformSize[subIndex].sizeName,
                        remark: this.orderPlatformSize[subIndex].remark,
                        showName: (subIndex === 0)
                    };
                    // console.log(stockOne);

                    // 还原之前数据值
                    var oldStock = this.findSameStock(indexColor.seq, stockOne.sizeSeq);
                    if (oldStock) {
                        stockOne.stock = oldStock.stock;
                        if (oldStock.setStock) {
                            stockOne.setStock = oldStock.setStock;
                        }
                    }
                    newStockTableData.push(stockOne);
                }
            }

            this.setValueEvent = true;
            this.stockArray = newStockTableData;
            this.$emit('stockDataChange', this.stockArray);
        },
        findSameStock: function (seq, sizeSeq) {
            for (var oldIndex = 0; oldIndex < this.stockArray.length; oldIndex++) {
                var oldStock = this.stockArray[oldIndex];
                if (oldStock.seq == seq && oldStock.sizeSeq == sizeSeq) {
                    return oldStock;
                }
            }
            return null;
        }
    },
    props: {
        colorArray: {type: Array, default: []},
        sizeArray: {type: Array, default: []},
        stockArray: {type: Array, default: []},
        changeStock: {type: Boolean, default: false}
    },
    watch: {
        stockArray: function (array) {
            if (array.length == 0 && !this.setValueEvent) {
                /*新增界面初始化*/
                this.orderColorSelected = [];
                this.orderSizeSelected = [];
                this.orderPlatformColor = [];
                this.orderPlatformSize = [];
                for (var i = 0; i < this.sizeArray.length; i++) {
                    this.sizeArray[i].remark = "";
                }
                return;
            }
            if (this.setValueEvent) {
                this.setValueEvent = false;
                return;
            }
            if (array.length > 0 && array[0]["length"]) {
                return;
            }
            var stockObj = {}, sizeArray = [], sizeArray2 = [];
            for (var aIndex = 0; aIndex < array.length; aIndex++) {
                var oneStock = array[aIndex];
                var seqArray = stockObj[oneStock.seq];
                if (seqArray == null) {
                    seqArray = [];
                    stockObj[oneStock.seq] = seqArray;
                }
                seqArray.push(oneStock);

                var hasSize = -1;
                for (var sIndex = 0; sIndex < sizeArray.length; sIndex++) {
                    if (sizeArray[sIndex].seq == oneStock.sizeSeq) {
                        hasSize = sIndex;
                        break;
                    }
                }

                if (hasSize == -1) {
                    var tIndex = 0;
                    for (tIndex = 0; tIndex < this.sizeArray.length; tIndex++) {
                        if (this.sizeArray[tIndex].seq == oneStock.sizeSeq) {
                            this.sizeArray[tIndex].remark = oneStock.remark;
                            break;
                        }
                    }
                    sizeArray.push(this.sizeArray[tIndex]);
                    sizeArray2.push(this.sizeArray[tIndex]);
                }
            }
            var resultArray = [], colorArray = [], colorArray2 = [];
            for (var keySeq in stockObj) {
                var stockArray = stockObj[keySeq];
                colorArray.push({seq: keySeq, name: stockArray[0].name, code: stockArray[0].code});
                colorArray2.push({seq: keySeq, name: stockArray[0].name, code: stockArray[0].code});

                stockArray[0].length = stockArray.length;
                stockArray[0].showName = true;

                for (var subIndex = 1; subIndex < stockArray.length; subIndex++) {
                    stockArray[subIndex].showName = false;
                }
                resultArray = resultArray.concat(stockArray);
            }

            this.orderColorSelected = colorArray;
            this.orderPlatformColor = colorArray2;
            this.orderSizeSelected = sizeArray;
            this.orderPlatformSize = sizeArray2;
            this.stockArray = resultArray;
        }
    },
    /*template: `
     <div>
     <!--颜色-->
     <div class="form-group">
     <div class="col-xs-2 control-label" style="text-align: left;">颜色</div>
     <div class="col-xs-4">
     <select class="form-control" v-model="orderPlatformSelectColor">
     <option value="-1" selected="selected">选择颜色</option>
     <option v-for="(item,index) in colorArray" :value="index">{{item.name}}&nbsp;&nbsp;({{item.code}})</option>
     </select>
     </div>
     <div class="col-xs-4">
     <a class="btn btn-default" role="button" @click="orderPlatformAddColor">添加颜色</a>
     </div>
     </div>
     <div class="form-group">
     <div class="col-xs-3" v-for="item in orderColorSelected">
     <div class="checkbox">
     <label>
     <input type="checkbox" :value="item" v-model="orderPlatformColor" @click="orderPlatformColorCheck">{{item.name}}({{item.code}})
     </label>
     </div>
     </div>
     </div>
     <!--尺码-->
     <div class="form-group">
     <div class="col-xs-2 control-label" style="text-align: left;">尺码</div>
     <div class="col-xs-4">
     <select class="form-control" v-model="orderPlatformSelectSize">
     <option value="-1" selected="selected">选择尺码</option>
     <option v-for="(item,index) in sizeArray" :value="index">{{item.sizeName}}&nbsp;&nbsp;  ({{item.sizeCode}})</option>
     </select>
     </div>
     <div class="col-xs-4">
     <a class="btn btn-default" role="button" @click="orderPlatformAddSize">添加尺码</a>
     </div>
     </div>
     <div class="form-group">
     <div class="col-xs-5" v-for="item in orderSizeSelected">
     <div class="checkbox">
     <label>
     <input type="checkbox" :value="item" v-model="orderPlatformSize" @click="orderPlatformSizeCheck">{{item.sizeName}} ({{item.sizeCode}})
     </label>
     <input class="col-xs-7" style="float: right;padding-left: 5px;padding-right: 5px;" type="text" v-model="item.remark" maxlength="5" @input="remarkChange">
     </div>
     </div>
     </div>

     <!--  库存-->
     <div class="form-group" style="max-height: 200px;overflow-y: auto; border: #d2d0de 1px solid;">
     <table class="table table-hover">
     <tbody>
     <tr>
     <th>颜色</th>
     <th>尺码</th>
     <th>库存</th>
     <th v-if="changeStock">预扣</th>
     </tr>
     <tr v-for="item in stockArray">
     <td :rowspan="item.length" v-if="item.showName">{{item.name}}({{item.code}})</td>
     <td>{{item.sizeName}}({{item.sizeCode}})</td>
     <td><input v-model="item.stock"/></td>
     <td v-if="changeStock"><input v-model="item.setStock"/></td>
     </tr>
     </tbody>
     </table>
     </div>
     </div>
     `,*/

});

$(function () {
    var colModel = [
        {label: '分类', name: 'categoryName', width: 60, align: "center"},
        {label: '货品名称', name: 'goodName', width: 100, align: "center"},
        {label: '货号', name: 'goodID', width: 100, align: "center"},
        {
            label: '图片', name: 'img1', width: 120, align: "center", formatter: function (cellvalue) {
            var detail = '<image src="' + cellvalue + '" style="width: 75px;height: 75px;"/>';
            return detail;
        }
        }];
    //获取自定义属性列
    $.get(baseURL + "order/goods/getGoodsSXLabel", function (r) {
        if (r.code == 0) {
            $.each(r.result, function (key, value) {
                /*        console.log(key);
                 console.log(value);*/
                colModel.push(
                    {label: value, name: key, width: 100, align: "center"}
                );
            });
        }
        colModel.push(
            {label: '入库时间', name: 'inputTime', width: 150, align: "center"},
            {
                label: '操作', name: 'createDate', width: 150, align: "center",
                formatter: function (cellvalue, options, rowObject) {
                    var detail = '';
                    if (hasPermission('sys:platform:order')) {
                        if (rowObject.orderPlatformOnSale === false) {
                            detail += ('<button class="operation-btn-warn" onclick="upOrderSale(' + rowObject.seq + ')">订货上架</button><br/>');
                        } else if (rowObject.orderPlatformOnSale === true) {
                            detail += ('<button class="operation-btn-security" onclick="downOrderSale(' + rowObject.seq + ')">订货下架</button><br/>');
                        }
                    }
                    if (hasPermission('sys:platform:sale')) {
                        if (rowObject.onlineSaleOnSale === false) {
                            detail += ('<button class="operation-btn-warn" onclick="upOnlineSale(' + rowObject.seq + ')">商城上架</button><br/>');
                        } else if (rowObject.onlineSaleOnSale === true) {
                            detail += ('<button class="operation-btn-security" onclick="downOnlineSale(' + rowObject.seq + ')">商城下架</button><br/>');
                        }
                    }
                    detail += ('<button class="operation-btn-security" onclick="lineEdit(' + rowObject.seq + ')">修改</button>'
                    + '&nbsp'
                    + '<button class="operation-btn-dangery" onclick="del(' + rowObject.seq + ')">删除</button>');
                    return detail;
                }
            });
        loadGoodsGrid(colModel);
    });

    $('#onSaleTime').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        autoUpdateInput: false,
        timePicker24Hour: true,
        timePicker: true,
        locale: {
            format: 'YYYY/MM/DD HH:mm:ss',
            applyLabel: "应用",
            cancelLabel: "取消",
            customRangeLabel: '选择时间',
            daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
            monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            firstDay: 0
        }
    }).on('apply.daterangepicker', function (ev, picker) {
        vm.$set(vm.goodsDetail.orderPlatform, "onSaleTime", picker.startDate.format(picker.locale.format));
    });

    $('#offSaleTime').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        autoUpdateInput: false,
        timePicker24Hour: true,
        timePicker: true,
        locale: {
            format: 'YYYY/MM/DD HH:mm:ss',
            applyLabel: "应用",
            cancelLabel: "取消",
            customRangeLabel: '选择时间',
            daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
            monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            firstDay: 0
        }
    }).on('apply.daterangepicker', function (ev, picker) {
        vm.$set(vm.goodsDetail.orderPlatform, "offSaleTime", picker.startDate.format(picker.locale.format));
    });
});


function loadGoodsGrid(colModel) {
    $("#jqGrid").jqGrid({
        url: baseURL + 'order/goods/getGoodsList',
        datatype: "local",
        mtype: "POST",
        postData: {
            'categorySeq': vm.categorySeq,
            'periodSeq': vm.periodSeq,
            'keywords': vm.keywords,
            'onSale': vm.onSale,
        },
        colModel: colModel,
        height: 'auto',
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: false,
//	    shrinkToFit:false,
//	    autoScroll: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            // 隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({
                "overflow-x": "hidden"
            });
        }
    });
}

var setting = {
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
    callback: {
//    onClick: categoryClick
    }
};
var ztree;

var vm = new Vue({
    el: '#rrapp',
    components: {
        'stockItem': stockItem
    },
    data: {
        orderPlatformShow: false,
        onlineSaleShow: false,
        categoryArray: [],
        colorArray: [],
        sizeArray: [],
        sxArray: [],
        periods: {},

        // 表格加载条件
        currentPeriodYear: '',
        periodSeq: -1,
        selectPeriodName: '',
        categorySeq: '',
        selectCategoryName: '',
        keywords: '',
        onSale: -1,

        //界面显示属性
        showList: true,
        title: '',

        //颜色、尺码、库存
        orderPlatformStock: [],
        onlineSaleStock: [],

        // 商品详情内容
        goodsDetail: {
            orderPlatform: {author: [], onSale: false},
            onlineSale: {},
            description: [],
            banner: []
        },
        isShow: false
    },
    methods: {
        loadPeriod: function () {
            $.get(baseURL + "order/goods/periods", function (r) {
                /*        if (isBlank(vm.currentPeriodYear)) {
                 for (var key in r.periods) {
                 vm.currentPeriodYear = key;
                 vm.periodSeq = r.periods[key][0].seq;
                 break;
                 }
                 }*/
                vm.periods = r.periods;
                vm.reloadGoods(true);
            });
        },
        loadCategory: function () {
            // 加载分类树
            $.get(baseURL + "system/goodsCategory/list", function (r) {
                vm.categoryArray = r.list;
                vm.categoryArray.unshift({seq: -1, parentSeq: 0, name: "所有分类"})
                /*        var categorys = r.list;
                 categorys.push({seq: 0, pIdKey: -1, name: "所有分类"});
                 ztree = $.fn.zTree.init($("#categoryTree"), setting, categorys);
                 ztree.expandAll(true);*/
            });
        },
        loadColor: function () {
            $.get(baseURL + "system/goodsColor/alllist", function (r) {
                vm.colorArray = r.list;
            });
        },
        loadSize: function () {
            $.get(baseURL + "system/goodsSize/alllist", function (r) {

                if (r.list) {
                    vm.sizeArray = r.list.sort(sizeSortNumber);
                }
            });
        },
        loadGoodsSX: function () {
            $.get(baseURL + "order/goods/getGoodsSXOption", function (r) {
                if (r.code === 0 || r.code === '0') {
                    vm.sxArray = r.result;
                }
            });
        },
        saveOrUpdateGoods: function () {
            var formData = goodsUploadValidator();
            if (!formData) {
                return;
            }
            var url = vm.goodsDetail.seq ? "order/goods/updateGoods" : "order/goods/addGoods";
            parent.window.showLoading();
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: false,
                processData: false,
                data: formData,
                enctype: 'multipart/form-data',
                cache: false,
                success: function (r) {
                    if (r.code === 0) {
                        parent.window.hideLoading();
                        alert('操作成功', function () {
                            if (vm.goodsDetail.seq) {
                                vm.reloadGoods(false);
                            } else {
                                vm.reloadGoods(true);
                            }
                        });
                    } else {
                        parent.window.hideLoading();
                        alert(r.msg);
                    }
                },
                error: function () {
                    parent.window.hideLoading();
                    alert('服务器出错啦');
                }
            });
        },
        reloadGoods: function (toFirstPage) {
            vm.showList = true;
            if (!vm.periodSeq) {
                return;
            }

            var p = 1;
            if (!toFirstPage) {
                p = $("#jqGrid").jqGrid('getGridParam', 'page');//获取当前页
            }

            setTimeout(function () {
                $("#jqGrid").jqGrid('setGridParam', {
                    datatype: 'json',
                    postData: {
                        'categorySeq': vm.categorySeq,
                        'periodSeq': vm.periodSeq,
                        'keywords': vm.keywords
                    },
                    page: p,
                }).trigger('reloadGrid');
            }, 500);
        },
        addGoods: function () {
            if (isBlank(vm.periodSeq)) {
                alert("请先添加波次信息");
                return;
            }
            // 初始化参数
            vm.goodsDetail = {
                orderPlatform: {author: [], onSale: false},
                onlineSale: {},
                description: [],
                banner: []
            };
            vm.categoryName = '';
            clearVideo();

            vm.showList = false;
            vm.title = '添加货品';
            vm.orderPlatformShow = false;
            vm.onlineSaleShow = false;
            vm.orderPlatformStock = [];
            vm.onlineSaleStock = [];
        },
        /*    periodSelect: function (key, item) {
         vm.currentPeriodYear = key;
         vm.periodSeq = item.seq;

         vm.reloadGoods(true);
         },*/
        periodSelect: function (item) {
            if (item == -1) {
                vm.periodSeq = -1;
                vm.selectPeriodName = "所有波次";
            } else {
                vm.periodSeq = item.seq;
                vm.selectPeriodName = item.name;
            }
        },
        categorySelect: function (item) {
            vm.categorySeq = item.seq;
            vm.selectCategoryName = item.name;
        },
        handleDetailFileChange: function (event) {
            var inputDOM = event.target;
            var files = event.target.files;
            for (var i = 0; i < files.length; i++) {
                if (files[i].size >= 10 * 1024 * 1024) {
                    alert("文件超过10M啦！");
                    return;
                }
            }
            console.log(vm.goodsDetail.description)
            randerImages(0, inputDOM, vm.goodsDetail.description);
        },
        handleBandFileChange: function (value) {
            var inputDOM = value.target;
            if (inputDOM.files.length + vm.goodsDetail.banner.length > 5) {
                alert("轮播图不能超过5张！");
                return;
            }
            for (var fileSize = 0; fileSize < inputDOM.files.length; fileSize++) {
                var size = Math.floor(inputDOM.files[fileSize].size / 1024 / 1024);
                if (size > 10) {
                    alert("文件超过10M啦！");
                    return;
                }
            }

            randerImages(0, inputDOM, vm.goodsDetail.banner);
        },
        handleVideoFileChange: function (value) {
            var inputDOM = value.target;

            var size = Math.floor(inputDOM.files[0].size / 1024 / 1024);

            if (size > 20) {
                alert("文件超过20M啦！");
                return;
            }
            vm.goodsDetail.videoFile = inputDOM.files[0];
        },
        periodChoose: function (item) {
            vm.$set(vm.goodsDetail, "periodName", item.name);
            vm.goodsDetail.periodSeq = item.seq;
        },
        categoryChoose: function (item) {
            vm.$set(vm.goodsDetail, "categoryName", item.name);
            vm.goodsDetail.categorySeq = item.seq;
        },
        descriptionDel: function (index) {
            confirm("确定删除？", function () {
                vm.goodsDetail.description.splice(index, 1);
            });
        },
        bannerDel: function (index) {
            confirm("确定删除？", function () {
                vm.goodsDetail.banner.splice(index, 1);
            });
        },
        dragStartImage: function (index, event) {
            // console.log("moveImage:", index, event)
            var fromTarget = event.target;
            var imageType = fromTarget.parentNode.parentNode.id;
            // console.log("fromTarget:", fromTarget, imageType)
            event.dataTransfer.setData("fromIndex", index);
            event.dataTransfer.setData("fromType", imageType);
        },
        dragEnterImage: function (index, event) {
            // console.log("dragEnterImage:",index,event)
            //目标元素边框改为红色
            var toTarget = event.target;
            // console.log("改变相框颜色为红色")
            toTarget.parentNode.style.border = "1px solid red";
        },
        dragOverImage: function (index, event) {
            // console.log("dragOverImage:",index,event)
        },
        dragLeaveImage: function (index, event) {
            // console.log("dragLeaveImage:",index,event)
            // console.log("还原相框颜色")
            var toTarget = event.target;
            toTarget.parentNode.removeAttribute("style");
        },
        dropImage: function (index, event) {
            // console.log("dropImage:", index, event);
            var toTarget = event.target;
            var imageType = toTarget.parentNode.parentNode.id;
            // console.log("toTarget:", toTarget, imageType)

            var fromIndex = event.dataTransfer.getData("fromIndex");
            var fromType = event.dataTransfer.getData("fromType");

            //去除红色边框
            toTarget.parentNode.removeAttribute("style");

            //判断源对象和目标对象是否相同
            if (fromType == imageType && fromIndex != index) {
                //相同类型的不同图片进行排序操作
                // console.log("图片位置进行交换...")
                if (imageType == "bannerImages") {
                    //轮播图
                    swapArr(vm.goodsDetail.banner, fromIndex, index);
                } else {
                    //描述图
                    swapArr(vm.goodsDetail.description, fromIndex, index);
                }
            } else {
                //不同类型的图片或同类型索引相同的图片不进行操作
                // console.log("忽略不操作")
            }

        },
        search: function () {
            $("#jqGrid").jqGrid('setGridParam', {
                datatype: 'json',
                postData: {
                    'categorySeq': vm.categorySeq,
                    'periodSeq': vm.periodSeq,
                    'keywords': vm.keywords
                },
                page: 1,
            }).trigger('reloadGrid');
        },

        /*查询订货、商城 上架或待上架列表*/
        upLoad: function (num) {
            this.onSale = num;
            setTimeout(function () {
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: {
                        'onSale': vm.onSale,
                    },
                    page: 1,
                }).trigger('reloadGrid');
            }, 100);
        },

        exportQRCode: function () {
            var form = document.getElementById('exportQRCodeForm');
            form.action = baseURL + "order/goods/exportQRCode";
            form.token.value = token;
            form.submit();
        },

        exportWxQRCode: function () {
            var form = document.getElementById('exportQRCodeForm');
            form.action = baseURL + "order/goods/miniAppCode";
            form.token.value = token;
            form.categorySeq.value = vm.categorySeq;
            form.periodSeq.value = vm.periodSeq;
            form.keywords.value = vm.keywords;
            form.onSale.value = vm.onSale;
            form.submit();
        },
        uploadGoodsExcel: function () {
            parent.window.showLoading();
            $('#goodsExcelForm').ajaxSubmit({
                url: baseURL + 'order/goods/uploadOnlineGoodsExcel',
                dataType: 'json',
                success: function (r) {
                    if (r.code === 0) {
                        alert(r.msg, function () {
                            location.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                    $("#goodsExcelForm").resetForm();
                    parent.window.hideLoading();
                },
                error: function (r) {
                    alert("导入商品出错！");
                    $("#goodsExcelForm").resetForm();
                    parent.window.hideLoading();
                }
            });
        },
        /* 同步货品 */
        syncFromErp: function () {
            parent.window.showLoading();
            $.get(baseURL + "system/erp/sync/goods", function (r) {
                parent.window.hideLoading();
                alert("同步结束", function () {
                    location.reload();
                });
            });
        }
    },
    created: function () {
        this.loadPeriod();
        this.loadCategory();
        this.loadColor();
        this.loadSize();
        this.loadGoodsSX();
    }
});

function randerImages(fileIndex, inputDOM, vmData) {
    if (fileIndex < inputDOM.files.length) {
        var desImage = {file: inputDOM.files[fileIndex]};
        if (!desImage.file || !window.FileReader) return;
        if (/^image/.test(desImage.file.type)) {
            var reader = new FileReader();
            reader.readAsDataURL(desImage.file);
            reader.onloadend = function () {
                desImage.image = this.result;
                vmData.push(desImage);
                /* if (fileIndex + 1 === inputDOM.files.length) {
                 inputDOM.type = 'text';
                 inputDOM.type = 'file';
                 }*/
                randerImages(++fileIndex, inputDOM, vmData);
            }
        }
    }
}

function lineEdit(seq) {
    $.get(baseURL + "order/goods/edit?seq=" + seq, function (r) {
        var goodsDetail = r.goods;
        var banners = goodsDetail.banners;
        goodsDetail.banner = [];
        for (var index in banners) {
            goodsDetail.banner.push({image: banners[index]});
        }
        goodsDetail.description = [];
        var descriptions = r.goods.descriptions;
        for (var index in descriptions) {
            goodsDetail.description.push({image: descriptions[index]});
        }

        //处理属性
        // console.log(goodsDetail.sxArray);
        try {
            var sxJson = JSON.parse(goodsDetail.sxArray);
            for (var key in sxJson) {
                $('#' + key.toLocaleUpperCase()).val(sxJson[key]);
            }
        } catch (e) {
            console.error(e);
        }


        if (!isBlank(goodsDetail.orderPlatform)) {
            vm.orderPlatformShow = true;
            vm.orderPlatformStock = goodsDetail.orderPlatform.stock;
        } else {
            vm.orderPlatformShow = false;
            goodsDetail.orderPlatform = {author: []};
            vm.orderPlatformStock = [];
        }

        if (!isBlank(goodsDetail.onlineSale)) {
            vm.onlineSaleShow = true;
            vm.onlineSaleStock = goodsDetail.onlineSale.stock;
        } else {
            vm.onlineSaleShow = false;
            goodsDetail.onlineSale = {};
            vm.onlineSaleStock = [];
        }

        vm.goodsDetail = goodsDetail;
        vm.showList = false;
        vm.title = "修改商品";
    });
}

function del(id) {
    confirm('确定要删除？', function () {
        $.get(baseURL + "order/goods/delete?seq=" + id, function (r) {
            if (r.code = 1) {
                vm.reloadGoods(false);
            }
        });
    });
}

/*function categoryClick() {
 var nodes = ztree.getSelectedNodes();
 if (nodes.length > 0) {
 var node = nodes[0];
 if (node.seq === -1) {
 vm.categorySeq = '';
 } else {
 vm.categorySeq = node.seq;
 }

 vm.reloadGoods(true);
 }
 }*/

function clearVideo() {
    var videoChoose = document.getElementById("videoChooseInput");
    videoChoose.type = 'text';
    videoChoose.type = 'file';
}

function
goodsUploadValidator() {
    var formData = new FormData();

    if (isBlank(vm.goodsDetail.periodSeq)) {
        alert("波次不能为空");
        return;
    }
    formData.append("periodSeq", vm.goodsDetail.periodSeq);

//  if (isBlank(vm.goodsDetail.introduce)) {
//    alert("商品介绍不能为空");
//    return;
//  }
    formData.append("introduce", vm.goodsDetail.introduce);

//  if (isBlank(vm.goodsDetail.goodName)) {
//    alert("商品名称不能为空");
//    return;
//  }
    formData.append("goodName", vm.goodsDetail.goodName);

    if (isBlank(vm.goodsDetail.goodId)) {
        alert("货号不能为空");
        return;
    }
    formData.append("goodId", vm.goodsDetail.goodId);

    if (isBlank(vm.goodsDetail.categorySeq)) {
        alert("分类不能为空");
        return;
    }
    formData.append("categorySeq", vm.goodsDetail.categorySeq);

    var selectObj = {};
    var sxSelectArray = $('.options-select');
    $.each(sxSelectArray, function (index, element) {
        var id = $(element).attr("id");
        var selectValue = $(element).val();
        selectObj[id] = selectValue;
    });
    formData.append("sxArray", JSON.stringify(selectObj));

//  if (vm.goodsDetail.description.length <= 0) {
//    alert("产品详细描述不能为空");
//    return;
//  }
    for (var desIndex = 0; desIndex < vm.goodsDetail.description.length; desIndex++) {
        var desFileData = vm.goodsDetail.description[desIndex].file || new Blob();
        formData.append("descriptionFiles", desFileData);
        var desImageData = vm.goodsDetail.description[desIndex].image || "";
        // 是上传图片的 内容不需要上传
        formData.append("descriptions", desImageData.indexOf("data:image") > -1 ? "" : desImageData);
    }

//  if (vm.goodsDetail.banner.length <= 0) {
//    alert("轮播图片不能为空");
//    return;
//  }
    for (var banIndex = 0; banIndex < vm.goodsDetail.banner.length; banIndex++) {
        var banFileData = vm.goodsDetail.banner[banIndex].file || new Blob();
        formData.append("bannerFiles", banFileData);
        var banImageData = vm.goodsDetail.banner[banIndex].image || "";
        // 是上传图片的 内容不需要上传
        formData.append("banners", banImageData.indexOf("data:image") > -1 ? "" : banImageData);
    }

    if (vm.goodsDetail.videoFile) {
        formData.append("videoFile", vm.goodsDetail.videoFile);
    }
    formData.append("video", vm.goodsDetail.video || '');
    if (vm.goodsDetail.seq) {
        formData.append("seq", vm.goodsDetail.seq);
    }

    formData.append("isHotSale", vm.goodsDetail.isHotSale || false);
    formData.append("isNewest", vm.goodsDetail.isNewest || false);

    // 订货平台选中
    if (vm.orderPlatformShow) {
        if (isBlank(vm.goodsDetail.orderPlatform.oemPrice)) {
            alert("总代商价格未设置");
            return;
        }
        formData.append("orderPlatform.oemPrice", vm.goodsDetail.orderPlatform.oemPrice);

        if (isBlank(vm.goodsDetail.orderPlatform.wholesalerPrice)) {
            alert("批发商价格未设置");
            return;
        }
        formData.append("orderPlatform.wholesalerPrice", vm.goodsDetail.orderPlatform.wholesalerPrice);

        if (isBlank(vm.goodsDetail.orderPlatform.storePrice)) {
            alert("直营店价格未设置");
            return;
        }
        formData.append("orderPlatform.storePrice", vm.goodsDetail.orderPlatform.storePrice);

        /*2018-10-18需求，隐藏掉建议零售价的编辑，默认值0*/
//    if (isBlank(vm.goodsDetail.orderPlatform.salePrice)) {
//      alert("零售价格未设置");
//      return;
//    }
//    formData.append("orderPlatform.salePrice", vm.goodsDetail.orderPlatform.salePrice);
        formData.append("orderPlatform.salePrice", 0);

        formData.append("orderPlatform.onSale", vm.goodsDetail.orderPlatform.onSale || false);

        if (!vm.goodsDetail.orderPlatform.onSale && !isBlank(vm.goodsDetail.orderPlatform.onSaleTime)) {
            formData.append("orderPlatform.onSaleTime", vm.goodsDetail.orderPlatform.onSaleTime);
        }

        if (!isBlank(vm.goodsDetail.orderPlatform.offSaleTime)) {
            formData.append("orderPlatform.offSaleTime", vm.goodsDetail.orderPlatform.offSaleTime);
        }

        if (vm.goodsDetail.orderPlatform.author.length <= 0) {
            alert("订货平台权限未选择");
            return;
        }
        for (var auIndex = 0; auIndex < vm.goodsDetail.orderPlatform.author.length; auIndex++) {
            formData.append("orderPlatform.author[" + auIndex + "]", vm.goodsDetail.orderPlatform.author[auIndex]);
        }

        if (vm.orderPlatformStock.length <= 0) {
            alert("订货平台库存未设置");
            return;
        }
        for (var stIndex = 0; stIndex < vm.orderPlatformStock.length; stIndex++) {
            formData.append("orderPlatform.stock[" + stIndex + "]", JSON.stringify(vm.orderPlatformStock[stIndex]));
        }
    }

    // 商城平台选中
    if (vm.onlineSaleShow) {
        if (isBlank(vm.goodsDetail.onlineSale.tagPrice)) {
            alert("吊牌价未设置");
            return;
        }
        formData.append("onlineSale.tagPrice", vm.goodsDetail.onlineSale.tagPrice);

        if (isBlank(vm.goodsDetail.onlineSale.salePrice)) {
            alert("零售价未设置");
            return;
        }
        formData.append("onlineSale.salePrice", vm.goodsDetail.onlineSale.salePrice);

        formData.append("onlineSale.onSale", vm.goodsDetail.onlineSale.onSale || false);

        if (vm.onlineSaleStock.length <= 0) {
            alert("商城平台库存未设置");
            return;
        }
        for (var stockIndex = 0; stockIndex < vm.onlineSaleStock.length; stockIndex++) {
            formData.append("onlineSale.stock[" + stockIndex + "]", JSON.stringify(vm.onlineSaleStock[stockIndex]));
        }
    }
    return formData;
}

function upOrderSale(seq) {
    confirm("确定要上架订货平台吗？", function () {
        upAndDown(seq, 0, 1);
    });
}

function downOrderSale(seq) {
    confirm("确定要下架订货平台吗？", function () {
        upAndDown(seq, 0, 0);
    });
}

function upOnlineSale(seq) {
    confirm("确定要上架商城平台吗？", function () {
        upAndDown(seq, 1, 1);
    });
}

function downOnlineSale(seq) {
    confirm("确定要下架商城平台吗？", function () {
        upAndDown(seq, 1, 0);
    });
}

function upAndDown(seq, platform, upDown) {
    $.post(baseURL + "order/goods/updown",
        {seq: seq, platform: platform, updown: upDown},
        function (data) {
            if (data.code === 0) {
                vm.reloadGoods(false);
            } else {
                alert(data.msg);
            }
        });
}

//ajax 方式提交form表单，导入素材压缩包
function uploadSourceZip() {
    var fileDir = $("#sourceZipFile").val();
    var suffix = fileDir.substr(fileDir.lastIndexOf("."));
    if ("" == fileDir) {
        alert("选择需要导入的压缩包文件！");
        return false;
    }
    if (".rar" != suffix && ".zip" != suffix) {
        alert("选择压缩包格式的文件导入！");
        return false;
    }

    $('#sourceZipForm').ajaxSubmit({
        url: baseURL + 'order/goods/uploadSourceZip',
        dataType: 'json',
        success: function (r) {
            if (r.code === 0) {
                alert(r.msg);
                $("#sourceZipFile").val("");
                vm.reloadGoods(true);
            } else {
                alert(r.msg);
                $("#sourceZipFile").val("");
            }
        },
        error: function (r) {
            alert("导入素材出错！");
            $("#sourceZipFile").val("");
        },
    });
}

/*对尺码大小进行排序    sizeArray*/
function sizeSortNumber(a, b) {
    var _a = parseInt(a.sizeName).toString();
    var _b = parseInt(b.sizeName).toString();
    if (_a == "NaN") {
        return 1;
    }
    if (_b == "NaN") {
        return -1;
    }
    return _a - _b;
}
