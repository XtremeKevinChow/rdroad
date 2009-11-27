'=================================================
'Class ExcelApp
'=================================================
Public Class ExcelApp
    Private m_oApp As Object

    Public ReadOnly Property COMObject() As Object
        Get
            Return m_oApp
        End Get
    End Property

    Public Sub New()
        m_oApp = CreateObject("Excel.Application")
        If m_oApp Is Nothing Then
            Err.Raise(vbObjectError + 3000, "", "Can not create Excel.Application object!")
        End If
        DisplayAlerts = False
    End Sub

    Public Function Open(ByVal FileName As String) As ExcelWorkbook
        Dim oWorkBook As Object, oResult As ExcelWorkbook
        oResult = Nothing
        oWorkBook = m_oApp.Workbooks.Open(FileName)
        If Not (oWorkBook Is Nothing) Then
            oResult = New ExcelWorkbook(oWorkBook)
        End If
        Return oResult
    End Function

    ''' <summary>
    ''' 重载Open方法
    ''' </summary>
    ''' <param name="FileName"></param>
    ''' <param name="UpdateLinks">
    ''' <para>这个参数可选。指定文件中链接的更新方式。如果省略本参数，则提示用户选择链接的更新方式。否则，该参数的取值应为下表中的某个值。</para>
    ''' <para>0 不更新任何引用。 </para>
    ''' <para>1 更新外部引用，但不更新远程引用。 </para>
    ''' <para>2 更新远程引用，但不更新外部引用。 </para>
    ''' <para>3 同时更新远程引用和外部引用。</para>
    ''' </param>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Function Open(ByVal FileName As String, ByVal UpdateLinks As Int32) As ExcelWorkbook
        Dim oWorkBook As Object, oResult As ExcelWorkbook
        oResult = Nothing
        oWorkBook = m_oApp.Workbooks.Open(FileName, UpdateLinks)
        If Not (oWorkBook Is Nothing) Then
            oResult = New ExcelWorkbook(oWorkBook)
        End If
        Return oResult
    End Function

    Public Function Save(ByVal FileName As String) As ExcelWorkbook
        Dim oWorkBook As Object, oResult As ExcelWorkbook
        oResult = Nothing
        oWorkBook = m_oApp.Workbooks.Add()
        If Not (oWorkBook Is Nothing) Then
            oWorkBook.SaveAs(FileName)
            oResult = New ExcelWorkbook(oWorkBook)
        End If
        Return oResult
    End Function

    ''' <summary>
    ''' Index：对象的名称或索引号
    ''' </summary>
    ''' <param name="Index"></param>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Overloads Function Workbooks(ByVal Index As Integer) As ExcelWorkbook
        Dim oWorkBook As Object, oResult As ExcelWorkbook
        oResult = Nothing
        oWorkBook = m_oApp.Workbooks(Index)
        If Not (oWorkBook Is Nothing) Then
            oResult = New ExcelWorkbook(oWorkBook)
        End If
        Return oResult
    End Function

    Public ReadOnly Property Version() As String
        Get
            Dim sResult As String
            sResult = ""
            If Not (m_oApp Is Nothing) Then
                sResult = m_oApp.Version
            End If
            Return sResult
        End Get
    End Property

    Public ReadOnly Property Name() As String
        Get
            Dim sResult As String
            sResult = ""
            If Not (m_oApp Is Nothing) Then
                sResult = m_oApp.Name
            End If
            Return sResult
        End Get
    End Property

    ''' <summary>
    ''' <para>如果宏运行时 Microsoft Excel 显示特定的警告和消息，则该值为 True。Boolean 类型，可读写。</para>
    ''' <para>默认值为 True。如果不想在宏运行时被无穷无尽的提示和警告消息所困扰，请将本属性设置为 False；</para>
    ''' <para>这样每次出现需用户应答的消息时，Microsoft Excel 将选择默认应答。</para>
    ''' <para>如果将该属性设置为 False，则在代码运行结束后，Micorosoft Excel 将该属性设置为 True，除非正运行交叉处理代码。</para>
    ''' <para>如果使用工作簿的 SaveAs 方法覆盖现有文件，“覆盖”警告默认为“No”，当 DisplayAlerts 属性值设置为 True 时，Excel 选择“Yes”。</para>
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property DisplayAlerts() As Boolean
        Get
            If Not (m_oApp Is Nothing) Then
                DisplayAlerts = m_oApp.DisplayAlerts
            Else
                DisplayAlerts = False
            End If
        End Get
        Set(ByVal Value As Boolean)
            If Not (m_oApp Is Nothing) Then
                m_oApp.DisplayAlerts = Value
            End If
        End Set
    End Property

    ''' <summary>
    ''' <para>如果屏幕更新功能是打开的，则该值为 True。Boolean 类型，可读写。</para>
    ''' <para>说明</para>
    ''' <para>关闭屏幕更新可加快宏的执行速度。这样将看不到宏的执行过程，但宏的执行速度加快了。</para>
    ''' <para>当宏结束运行后，请记住将 ScreenUpdating 属性设回到 True。</para>
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property ScreenUpdating() As Boolean
        Get
            If Not (m_oApp Is Nothing) Then
                ScreenUpdating = m_oApp.ScreenUpdating
            Else
                ScreenUpdating = False
            End If
        End Get
        Set(ByVal Value As Boolean)
            If Not (m_oApp Is Nothing) Then
                m_oApp.ScreenUpdating = Value
            End If
        End Set
    End Property

    Public Function NewWorkBook() As ExcelWorkbook
        Dim oWorkBook As Object, oResult As ExcelWorkbook
        oResult = Nothing
        oWorkBook = m_oApp.Workbooks.Add()
        If Not (oWorkBook Is Nothing) Then
            oResult = New ExcelWorkbook(oWorkBook)
        End If
        Return oResult
    End Function

    Public Sub Quit()
        On Error Resume Next
        If Not (m_oApp Is Nothing) Then
            m_oApp.Quit()
        End If
        On Error GoTo -1
    End Sub

    Protected Overrides Sub Finalize()
        Quit()
        MyBase.Finalize()
    End Sub
End Class

'=================================================
'Class ExcelWorkbook
'=================================================
Public Class ExcelWorkbook
    Private m_oWorkbook As Object

    Public ReadOnly Property COMObject() As Object
        Get
            Return m_oWorkbook
        End Get
    End Property

    Private Sub New()
    End Sub
    Public Sub New(ByVal Workbook As Object)
        m_oWorkbook = Workbook
    End Sub

    ''' <summary>
    ''' 是否已经保存。如果有修改内容，关闭workbook前不希望保存，请先将Saved属性设置为true，否则excel对象会弹出一个询问是否保存的对话框，程序代码的执行被中断掉
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property Saved() As Boolean
        Get
            If Not (m_oWorkbook Is Nothing) Then
                Saved = m_oWorkbook.Saved
            Else
                Saved = False
            End If
        End Get
        Set(ByVal Value As Boolean)
            If Not (m_oWorkbook Is Nothing) Then
                m_oWorkbook.Saved = Value
            End If
        End Set
    End Property

    Public Sub Save()
        If Not (m_oWorkbook Is Nothing) Then
            m_oWorkbook.Save()
        End If
    End Sub

    Public Sub SaveAs(ByVal FileName As String)
        If Not (m_oWorkbook Is Nothing) Then
            m_oWorkbook.SaveAs(FileName)
        End If
    End Sub

    ''' <summary>
    ''' <para>获取worksheet对象，索引<paramref name="Index">Index</paramref>从1开始</para>
    ''' <para>指定索引的worksheet对象存在，则返回该对象；否则新建一个worksheet并返回</para>
    ''' </summary>
    ''' <param name="Index"></param>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Overloads Function Worksheets(ByVal Index As Integer) As ExcelWorksheet
        Dim oWorkSheet As Object, oResult As ExcelWorksheet
        oResult = Nothing
        If m_oWorkbook.Worksheets.Count < 1 Then
            oWorkSheet = m_oWorkbook.Worksheets.Add()
        Else
            If Index <= 0 Then
                oWorkSheet = m_oWorkbook.Worksheets(1)
            ElseIf Index > m_oWorkbook.Worksheets.Count Then
                oWorkSheet = m_oWorkbook.Worksheets.Add(, m_oWorkbook.Worksheets(m_oWorkbook.Worksheets.Count))
            Else
                oWorkSheet = m_oWorkbook.Worksheets(Index)
            End If
        End If

        If Not (oWorkSheet Is Nothing) Then
            oResult = New ExcelWorksheet(oWorkSheet)
        End If
        Return oResult
    End Function

    Public Sub Close()
        If Not (m_oWorkbook Is Nothing) Then
            Saved = True
            m_oWorkbook.Close()
        End If
    End Sub
End Class

'=================================================
'Class ExcelWorksheet
'=================================================
Public Class ExcelWorksheet
    Private m_oWorksheet As Object

    Public ReadOnly Property COMObject() As Object
        Get
            Return m_oWorksheet
        End Get
    End Property

    Private Sub New()
    End Sub
    Public Sub New(ByVal Worksheet As Object)
        m_oWorksheet = Worksheet
    End Sub

    Public Property Name() As String
        Get
            Dim oName As String
            oName = ""
            If Not (m_oWorksheet Is Nothing) Then
                oName = m_oWorksheet.Name
            End If
            Return oName
        End Get
        Set(ByVal Value As String)
            If Not (m_oWorksheet Is Nothing) Then
                m_oWorksheet.Name = Value
            End If
        End Set
    End Property

    ''' <summary>
    ''' 指定位置的单元格对象
    ''' </summary>
    ''' <param name="RowIndex"></param>
    ''' <param name="ColumnIndex"></param>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Overloads Function Cells(ByVal RowIndex As Integer, ByVal ColumnIndex As Integer) As ExcelRange
        Dim oExcelRange As Object, oResult As ExcelRange
        oResult = Nothing
        oExcelRange = m_oWorksheet.Cells(RowIndex, ColumnIndex)
        If Not (oExcelRange Is Nothing) Then
            oResult = New ExcelRange(oExcelRange)
        End If
        Return oResult
    End Function
    ''' <summary>
    ''' 全部单元格对象
    ''' </summary>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Overloads Function Cells() As ExcelRange
        Dim oExcelRange As Object, oResult As ExcelRange
        oResult = Nothing
        oExcelRange = m_oWorksheet.Cells
        If Not (oExcelRange Is Nothing) Then
            oResult = New ExcelRange(oExcelRange)
        End If
        Return oResult
    End Function

    '相当于原来的Worksheets("Sheet1").Range("A1:B3")返回从A1到B3的Range
    'Worksheets("Sheet1").Range("A1")返回单元格A1
    Public Overloads Function Range(ByVal strRange As String) As ExcelRange
        Dim oExcelRange As Object, oResult As ExcelRange
        oResult = Nothing
        oExcelRange = m_oWorksheet.Range(strRange)
        If Not (oExcelRange Is Nothing) Then
            oResult = New ExcelRange(oExcelRange)
        End If
        Return oResult
    End Function
    ''' <summary>
    ''' 相当于原来Worksheets(1).Range("C5:C10").Cells(1, 1)返回的C5到C10的Range中的第一行第一列的单元格(也是表示为Range)
    ''' </summary>
    ''' <param name="RowBegin"></param>
    ''' <param name="RowEnd"></param>
    ''' <param name="ColumnBegin"></param>
    ''' <param name="ColumnEnd"></param>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Overloads Function Range(ByVal RowBegin As Integer, ByVal RowEnd As Integer, ByVal ColumnBegin As Integer, ByVal ColumnEnd As Integer) As ExcelRange
        Dim oExcelRange As Object, oResult As ExcelRange
        oResult = Nothing
        oExcelRange = m_oWorksheet.Range(m_oWorksheet.Cells(RowBegin, ColumnBegin), m_oWorksheet.Cells(RowEnd, ColumnEnd))
        If Not (oExcelRange Is Nothing) Then
            oResult = New ExcelRange(oExcelRange)
        End If
        Return oResult
    End Function

    ''' <summary>
    ''' 激活当前工作表
    ''' </summary>
    ''' <remarks></remarks>
    Public Sub Activate()
        If Not (m_oWorksheet Is Nothing) Then
            m_oWorksheet.Activate()
        End If
    End Sub

    ''' <summary>
    ''' 将剪贴板中的内容粘贴到工作表的当前选定区域Range
    ''' </summary>
    ''' <remarks></remarks>
    Public Sub Paste()
        If Not (m_oWorksheet Is Nothing) Then
            m_oWorksheet.Paste()
        End If
    End Sub

    ''' <summary>
    ''' 当前选择的区域
    ''' </summary>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public ReadOnly Property Selection() As ExcelRange
        Get
            If Not (m_oWorksheet Is Nothing) Then
                Return m_oWorksheet.selection
            End If
            Return Nothing
        End Get
    End Property
End Class

'=================================================
'Class ExcelRange
'=================================================
Public Class ExcelRange
    Private m_oRange As Object

    Private Sub New()
    End Sub
    Public Sub New(ByVal Range As Object)
        m_oRange = Range
    End Sub

    Default Public ReadOnly Property Item(ByVal RowIndex As Integer, ByVal ColumnIndex As Integer) As ExcelRange
        Get
            Dim oExcelRange As Object, oResult As ExcelRange
            oResult = Nothing
            oExcelRange = Nothing
            If Not (m_oRange Is Nothing) Then
                oExcelRange = m_oRange(RowIndex, ColumnIndex)
            End If
            If Not (oExcelRange Is Nothing) Then
                oResult = New ExcelRange(oExcelRange)
            End If
            Return oResult
        End Get
    End Property
    '相当于原来的Worksheets("Sheet1").Range("A1:B3")返回从A1到B3的Range
    'Worksheets("Sheet1").Range("A1")返回单元格A1
    Default Public ReadOnly Property Item(ByVal Key As String) As ExcelRange
        Get
            Dim oExcelRange As Object, oResult As ExcelRange
            oResult = Nothing
            oExcelRange = Nothing
            If Not (m_oRange Is Nothing) Then
                oExcelRange = m_oRange(Key)
            End If
            If Not (oExcelRange Is Nothing) Then
                oResult = New ExcelRange(oExcelRange)
            End If
            Return oResult
        End Get
    End Property
    ''' <summary>
    ''' 相当于原来Worksheets(1).Range("C5:C10").Cells(1, 1)返回的C5到C10的Range中的第一行第一列的单元格(也是表示为Range)
    ''' </summary>
    ''' <param name="Cells1"></param>
    ''' <param name="Cells2"></param>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Default Public ReadOnly Property Item(ByVal Cells1 As ExcelRange, ByVal Cells2 As ExcelRange) As ExcelRange
        Get
            Dim oExcelRange As Object, oResult As ExcelRange
            oResult = Nothing
            oExcelRange = Nothing
            If Not (m_oRange Is Nothing) Then
                oExcelRange = m_oRange(Cells1, Cells2)
            End If
            If Not (oExcelRange Is Nothing) Then
                oResult = New ExcelRange(oExcelRange)
            End If
            Return oResult
        End Get
    End Property

    '返回Range的边框||有以下常量(XlBordersIndex类型)：   
    '                               xlDiagonalDown=5    下对角线
    '                               xlDiagonalUp=6  上对角线
    '                               xlEdgeBottom=4  下边框
    '                               xlEdgeLeft=1    左边框
    '                               xlEdgeRight=2   右边框
    '                               xlEdgeTop=3 上边框
    '                               xlInsideHorizontal  
    '                               xlInsideVertical  
    Public Overloads Function Borders(ByVal BorderIndex As Object) As ExcelBorder
        Dim oBorder As Object, oResult As ExcelBorder
        oResult = Nothing
        oBorder = Nothing
        If Not (m_oRange Is Nothing) Then
            oBorder = m_oRange.Borders(BorderIndex)
        End If
        If Not (oBorder Is Nothing) Then
            oResult = New ExcelBorder(oBorder)
        End If
        Return oResult
    End Function

    Public Overloads Function Cells(ByVal RowIndex As Integer, ByVal ColumnIndex As Integer) As ExcelRange
        Dim oExcelRange As Object, oResult As ExcelRange
        oResult = Nothing
        oExcelRange = Nothing
        If Not (m_oRange Is Nothing) Then
            oExcelRange = m_oRange.Cells(RowIndex, ColumnIndex)
        End If
        If Not (oExcelRange Is Nothing) Then
            oResult = New ExcelRange(oExcelRange)
        End If
        Return oResult
    End Function

    Public ReadOnly Property Font() As ExcelFont
        Get
            Dim oExcelFont As Object, oResult As ExcelFont
            oResult = Nothing
            oExcelFont = Nothing
            If Not (m_oRange Is Nothing) Then
                oExcelFont = m_oRange.Font
            End If
            If Not (oExcelFont Is Nothing) Then
                oResult = New ExcelFont(oExcelFont)
            End If
            Return oResult
        End Get
    End Property

    ''' <summary>
    ''' 背景颜色
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public ReadOnly Property Interior() As ExcelInterior
        Get
            Dim oExcelInterior As Object, oResult As ExcelInterior
            oResult = Nothing
            oExcelInterior = Nothing
            If Not (m_oRange Is Nothing) Then
                oExcelInterior = m_oRange.Interior
            End If
            If Not (oExcelInterior Is Nothing) Then
                oResult = New ExcelInterior(oExcelInterior)
            End If
            Return oResult
        End Get
    End Property

    Public Property Value() As Object
        Get
            Dim oResult As Object
            oResult = Nothing
            If Not (m_oRange Is Nothing) Then
                oResult = m_oRange.Value
            End If
            Return oResult
        End Get
        Set(ByVal Value As Object)
            m_oRange.Value = Value
        End Set
    End Property

    Public ReadOnly Property Text() As String
        Get
            Dim oResult As String
            oResult = ""
            If Not (m_oRange Is Nothing) Then
                oResult = m_oRange.Text
            End If
            Return oResult
        End Get
    End Property

    ''' <summary>
    ''' 列宽||如果区域中所有列的列宽都相等，ColumnWidth 属性返回该宽度值。如果区域中的列宽不等，本属性返回 Null。
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property ColumnWidth() As Decimal
        Get
            Dim oResult As Decimal
            oResult = Nothing
            If Not (m_oRange Is Nothing) Then
                oResult = m_oRange.ColumnWidth
            End If
            Return oResult
        End Get
        Set(ByVal Value As Decimal)
            m_oRange.ColumnWidth = Value
        End Set
    End Property

    ''' <summary>
    ''' 行高||以磅为单位返回或设置指定区域中所有行的行宽。如果区域中的各行的行高不等，返回 Null
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property RowHeight() As Decimal
        Get
            Dim oResult As Decimal
            oResult = Nothing
            If Not (m_oRange Is Nothing) Then
                oResult = m_oRange.RowHeight
            End If
            Return oResult
        End Get
        Set(ByVal Value As Decimal)
            m_oRange.RowHeight = Value
        End Set
    End Property

    ''' <summary>
    ''' 合并单元格||设置为True，合并。否则，取消合并
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property MergeCells() As Boolean
        Get
            Dim oResult As Boolean
            oResult = Nothing
            If Not (m_oRange Is Nothing) Then
                oResult = m_oRange.MergeCells
            End If
            Return oResult
        End Get
        Set(ByVal Value As Boolean)
            m_oRange.MergeCells = Value
        End Set
    End Property

    ''' <summary>
    ''' 返回包含指定单元格(单个单元格，不是一片区域)的合并区域。如果指定的单元格不在合并区域内，则该属性返回指定的单元格。
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public ReadOnly Property MergeArea() As ExcelRange
        Get
            Dim oExcelRange As Object, oResult As ExcelRange
            oResult = Nothing
            oExcelRange = Nothing
            If Not (m_oRange Is Nothing) Then
                oExcelRange = m_oRange.MergeArea
            End If
            If Not (oExcelRange Is Nothing) Then
                oResult = New ExcelRange(oExcelRange)
            End If
            Return oResult
        End Get
    End Property

    '返回或设置横向对齐方式||有以下常量(XlHAlign类型)： 
    '                               xlHAlignCenter=3    居中
    '                               xlHAlignCenterAcrossSelection=7 跨列居中
    '                               xlHAlignDistributed=8   分散对齐（缩进）
    '                               xlHAlignFill=5  填充
    '                               xlHAlignGeneral=1   常规
    '                               xlHAlignJustify=6   两端对齐
    '                               xlHAlignLeft=2  靠左（缩进）
    '                               xlHAlignRight=4 靠右（缩进）
    Public Property HorizontalAlignment() As Object
        Get
            Dim oResult As Object
            oResult = Nothing
            If Not (m_oRange Is Nothing) Then
                oResult = m_oRange.HorizontalAlignment
            End If
            Return oResult
        End Get
        Set(ByVal Value As Object)
            m_oRange.HorizontalAlignment = Value
        End Set
    End Property

    '返回或设置纵向对齐方式||有以下常量(XlVAlign类型)： 
    '                               xlVAlignCenter=2    居中
    '                               xlVAlignJustify=4   两端对齐
    '                               xlVAlignBottom=3    靠下
    '                               xlVAlignDistributed=5   分散对齐 
    '                               xlVAlignTop=1   靠上
    Public Property VerticalAlignment() As Object
        Get
            Dim oResult As Object
            oResult = Nothing
            If Not (m_oRange Is Nothing) Then
                oResult = m_oRange.VerticalAlignment
            End If
            Return oResult
        End Get
        Set(ByVal Value As Object)
            m_oRange.VerticalAlignment = Value
        End Set
    End Property

    '复制当前Range到剪贴板
    '复制粘贴步骤：
    'Range("A1:A2").Copy
    'Range("C3:H12").SelectRange
    'Worksheets(1).Paste
    Public Sub Copy()
        m_oRange.Copy()
    End Sub

    ''' <summary>
    ''' 选定当前的Range
    ''' </summary>
    ''' <remarks></remarks>
    Public Sub SelectRange()
        m_oRange.Select()
    End Sub

    Public Sub AutoFit()
        m_oRange.EntireColumn.AutoFit()
    End Sub

    ''' <summary>
    ''' 返回或设置单元格的公式。传入的值类似："=A4+A10" 或者 "=$A$4+$A$10"
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property Formula() As String
        Get
            Dim oResult As Object
            oResult = Nothing
            If Not (m_oRange Is Nothing) Then
                oResult = m_oRange.Formula
            End If
            Return oResult
        End Get
        Set(ByVal Value As String)
            m_oRange.Formula = Value
        End Set
    End Property

    ''' <summary>
    ''' 替换
    ''' </summary>
    ''' <param name="what"></param>
    ''' <param name="replacement"></param>
    ''' <param name="matchCase"></param>
    ''' <remarks>
    ''' Excel.XlLookAt.xlPart: 2
    ''' Excel.XlLookAt.xlWhole: 1
    ''' 
    ''' Excel.XlSearchOrder.xlByRows: 1
    ''' Excel.XlSearchOrder.xlByColumns: 2
    ''' </remarks>
    Public Sub Replace(ByVal what As String, ByVal replacement As String, ByVal matchCase As Boolean)
        If Not (m_oRange Is Nothing) Then
            m_oRange.Replace(what:=what, replacement:=replacement, matchCase:=matchCase, LookAt:=2, SearchOrder:=1, SearchFormat:=False, ReplaceFormat:=False)
        End If
    End Sub
End Class

'=================================================
'Class ExcelFont
'=================================================
Public Class ExcelFont
    Private m_oFont As Object

    Private Sub New()
    End Sub
    Public Sub New(ByVal Font As Object)
        m_oFont = Font
    End Sub

    Public Property Bold() As Boolean
        Get
            If Not (m_oFont Is Nothing) Then
                Bold = m_oFont.Bold
            Else
                Bold = False
            End If
        End Get
        Set(ByVal Value As Boolean)
            If Not (m_oFont Is Nothing) Then
                m_oFont.Bold = Value
            End If
        End Set
    End Property

    Public ReadOnly Property Color() As Double
        Get
            Dim oResult As Double
            oResult = 0 '黑色
            If Not (m_oFont Is Nothing) Then
                oResult = m_oFont.Color
            End If
            Return oResult
        End Get
    End Property
    Public Sub SetColor(ByVal R As Integer, ByVal G As Integer, ByVal B As Integer)
        Dim intRgb As Double '必须用Double，用Integer会出现转换前后的值不同，因为m_oFont.Color在运行时是Double类型
        intRgb = Double.Parse(RGB(R, G, B))
        If Not (m_oFont Is Nothing) Then
            m_oFont.Color = intRgb
        End If
    End Sub

    ''' <summary>
    ''' 返回或设置字体样式||FontStyle = "Bold Italic" 设置加粗和倾斜
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property FontStyle() As String
        Get
            Dim oResult As String
            oResult = ""
            If Not (m_oFont Is Nothing) Then
                oResult = m_oFont.FontStyle
            End If
            Return oResult
        End Get
        Set(ByVal Value As String)
            If Not (m_oFont Is Nothing) Then
                m_oFont.FontStyle = Value
            End If
        End Set
    End Property

    Public Property Italic() As Boolean
        Get
            If Not (m_oFont Is Nothing) Then
                Italic = m_oFont.Italic
            Else
                Italic = False
            End If
        End Get
        Set(ByVal Value As Boolean)
            If Not (m_oFont Is Nothing) Then
                m_oFont.Italic = Value
            End If
        End Set
    End Property

    ''' <summary>
    ''' 返回或设置字体名称
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property Name() As String
        Get
            Dim oResult As String
            oResult = "宋体"
            If Not (m_oFont Is Nothing) Then
                oResult = m_oFont.Name
            End If
            Return oResult
        End Get
        Set(ByVal Value As String)
            If Not (m_oFont Is Nothing) Then
                m_oFont.Name = Value
            End If
        End Set
    End Property

    ''' <summary>
    ''' 设置空心字
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property OutlineFont() As Boolean
        Get
            If Not (m_oFont Is Nothing) Then
                OutlineFont = m_oFont.OutlineFont
            Else
                OutlineFont = False
            End If
        End Get
        Set(ByVal Value As Boolean)
            If Not (m_oFont Is Nothing) Then
                m_oFont.OutlineFont = Value
            End If
        End Set
    End Property

    Public Property Shadow() As Boolean
        Get
            If Not (m_oFont Is Nothing) Then
                Shadow = m_oFont.Shadow
            Else
                Shadow = False
            End If
        End Get
        Set(ByVal Value As Boolean)
            If Not (m_oFont Is Nothing) Then
                m_oFont.Shadow = Value
            End If
        End Set
    End Property

    Public Property Size() As Integer
        Get
            Dim oResult As Integer
            oResult = 9 '默认九号字
            If Not (m_oFont Is Nothing) Then
                oResult = m_oFont.Size
            End If
            Return oResult
        End Get
        Set(ByVal Value As Integer)
            If Not (m_oFont Is Nothing) Then
                m_oFont.Size = Value
            End If
        End Set
    End Property

    ''' <summary>
    ''' 文字中间是否有一条水平删除线
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property Strikethrough() As Boolean
        Get
            If Not (m_oFont Is Nothing) Then
                Strikethrough = m_oFont.Strikethrough
            Else
                Strikethrough = False
            End If
        End Get
        Set(ByVal Value As Boolean)
            If Not (m_oFont Is Nothing) Then
                m_oFont.Strikethrough = Value
            End If
        End Set
    End Property

    '下标
    Public Property Subscript() As Boolean
        Get
            If Not (m_oFont Is Nothing) Then
                Subscript = m_oFont.Subscript
            Else
                Subscript = False
            End If
        End Get
        Set(ByVal Value As Boolean)
            If Not (m_oFont Is Nothing) Then
                m_oFont.Subscript = Value
            End If
        End Set
    End Property

    '上标
    Public Property Superscript() As Boolean
        Get
            If Not (m_oFont Is Nothing) Then
                Superscript = m_oFont.Superscript
            Else
                Superscript = False
            End If
        End Get
        Set(ByVal Value As Boolean)
            If Not (m_oFont Is Nothing) Then
                m_oFont.Superscript = Value
            End If
        End Set
    End Property

    '返回或设置下划线类型||有以下常量(XlUnderlineStyle类型)：   
    '                               xlUnderlineStyleNone=1  无下划线 
    '                               xlUnderlineStyleSingle=2    单下划线
    '                               xlUnderlineStyleDouble=3    双下划线
    '                               xlUnderlineStyleSingleAccounting=4  会计用单下划线
    '                               xlUnderlineStyleDoubleAccounting=5  会计用双下划线
    Public Property Underline() As Object
        Get
            Dim oResult As Object
            oResult = Nothing
            If Not (m_oFont Is Nothing) Then
                oResult = m_oFont.Underline
            End If
            Return oResult
        End Get
        Set(ByVal Value As Object)
            m_oFont.Underline = Value
        End Set
    End Property
End Class

'=================================================
'Class ExcelBorder
'=================================================
Public Class ExcelBorder
    Private m_oBorder As Object

    Private Sub New()
    End Sub
    Public Sub New(ByVal Border As Object)
        m_oBorder = Border
    End Sub

    Public ReadOnly Property Color() As Object
        Get
            Dim oResult As Object
            oResult = Nothing
            If Not (m_oBorder Is Nothing) Then
                oResult = m_oBorder.Color
            End If
            Return oResult
        End Get
    End Property
    Public Sub SetColor(ByVal R As Integer, ByVal G As Integer, ByVal B As Integer)
        Dim intRgb As Double '必须用Double，用Integer会出现转换前后的值不同，因为m_oFont.Color在运行时是Double类型
        intRgb = Double.Parse(RGB(R, G, B))
        If Not (m_oBorder Is Nothing) Then
            m_oBorder.Color = intRgb
        End If
    End Sub

    '返回或设置边框的线型||有以下常量(XlLineStyle类型)：{0-13}   
    '                               xlContinuous=1  连续线
    '                               xlDash=3    线型线
    '                               xlDashDot=4 线点型线
    '                               xlDashDotDot=5  线点点型线
    '                               xlDot=2 点型线
    '                               xlDouble=9  双线型线
    '                               xlSlantDashDot=6    倾斜的线点型线
    '                               xlLineStyleNone=0   无
    Public Property LineStyle() As Object
        Get
            Dim oResult As Object
            oResult = Nothing
            If Not (m_oBorder Is Nothing) Then
                oResult = m_oBorder.LineStyle
            End If
            Return oResult
        End Get
        Set(ByVal Value As Object)
            m_oBorder.LineStyle = Value
        End Set
    End Property

    '返回或设置边框的粗细||有以下常量(XlBorderWeight类型)：   
    '                               xlHairline=1    特细 
    '                               xlThin=2    细
    '                               xlMedium=3  中等
    '                               xlThick=4   粗
    Public Property Weight() As Object
        Get
            Dim oResult As Object
            oResult = Nothing
            If Not (m_oBorder Is Nothing) Then
                oResult = m_oBorder.Weight
            End If
            Return oResult
        End Get
        Set(ByVal Value As Object)
            m_oBorder.Weight = Value
        End Set
    End Property
End Class

'=================================================
'Class ExcelInterior    表示对象的内部
'=================================================
Public Class ExcelInterior
    Private m_oInterior As Object
    Private Sub New()
    End Sub
    Public Sub New(ByVal Interior As Object)
        m_oInterior = Interior
    End Sub

    Public ReadOnly Property Color() As Object
        Get
            Dim oResult As Object
            oResult = Nothing
            If Not (m_oInterior Is Nothing) Then
                oResult = m_oInterior.Color
            End If
            Return oResult
        End Get
    End Property
    Public Sub SetColor(ByVal R As Integer, ByVal G As Integer, ByVal B As Integer)
        Dim intRgb As Double '必须用Double，用Integer会出现转换前后的值不同，因为m_oFont.Color在运行时是Double类型
        intRgb = Double.Parse(RGB(R, G, B))
        If Not (m_oInterior Is Nothing) Then
            m_oInterior.Color = intRgb
        End If
    End Sub
End Class