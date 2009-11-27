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
    ''' ����Open����
    ''' </summary>
    ''' <param name="FileName"></param>
    ''' <param name="UpdateLinks">
    ''' <para>���������ѡ��ָ���ļ������ӵĸ��·�ʽ�����ʡ�Ա�����������ʾ�û�ѡ�����ӵĸ��·�ʽ�����򣬸ò�����ȡֵӦΪ�±��е�ĳ��ֵ��</para>
    ''' <para>0 �������κ����á� </para>
    ''' <para>1 �����ⲿ���ã���������Զ�����á� </para>
    ''' <para>2 ����Զ�����ã����������ⲿ���á� </para>
    ''' <para>3 ͬʱ����Զ�����ú��ⲿ���á�</para>
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
    ''' Index����������ƻ�������
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
    ''' <para>���������ʱ Microsoft Excel ��ʾ�ض��ľ������Ϣ�����ֵΪ True��Boolean ���ͣ��ɶ�д��</para>
    ''' <para>Ĭ��ֵΪ True����������ں�����ʱ�������޾�����ʾ�;�����Ϣ�����ţ��뽫����������Ϊ False��</para>
    ''' <para>����ÿ�γ������û�Ӧ�����Ϣʱ��Microsoft Excel ��ѡ��Ĭ��Ӧ��</para>
    ''' <para>���������������Ϊ False�����ڴ������н�����Micorosoft Excel ������������Ϊ True�����������н��洦����롣</para>
    ''' <para>���ʹ�ù������� SaveAs �������������ļ��������ǡ�����Ĭ��Ϊ��No������ DisplayAlerts ����ֵ����Ϊ True ʱ��Excel ѡ��Yes����</para>
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
    ''' <para>�����Ļ���¹����Ǵ򿪵ģ����ֵΪ True��Boolean ���ͣ��ɶ�д��</para>
    ''' <para>˵��</para>
    ''' <para>�ر���Ļ���¿ɼӿ���ִ���ٶȡ����������������ִ�й��̣������ִ���ٶȼӿ��ˡ�</para>
    ''' <para>����������к����ס�� ScreenUpdating ������ص� True��</para>
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
    ''' �Ƿ��Ѿ����档������޸����ݣ��ر�workbookǰ��ϣ�����棬���Ƚ�Saved��������Ϊtrue������excel����ᵯ��һ��ѯ���Ƿ񱣴�ĶԻ��򣬳�������ִ�б��жϵ�
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
    ''' <para>��ȡworksheet��������<paramref name="Index">Index</paramref>��1��ʼ</para>
    ''' <para>ָ��������worksheet������ڣ��򷵻ظö��󣻷����½�һ��worksheet������</para>
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
    ''' ָ��λ�õĵ�Ԫ�����
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
    ''' ȫ����Ԫ�����
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

    '�൱��ԭ����Worksheets("Sheet1").Range("A1:B3")���ش�A1��B3��Range
    'Worksheets("Sheet1").Range("A1")���ص�Ԫ��A1
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
    ''' �൱��ԭ��Worksheets(1).Range("C5:C10").Cells(1, 1)���ص�C5��C10��Range�еĵ�һ�е�һ�еĵ�Ԫ��(Ҳ�Ǳ�ʾΪRange)
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
    ''' ���ǰ������
    ''' </summary>
    ''' <remarks></remarks>
    Public Sub Activate()
        If Not (m_oWorksheet Is Nothing) Then
            m_oWorksheet.Activate()
        End If
    End Sub

    ''' <summary>
    ''' ���������е�����ճ����������ĵ�ǰѡ������Range
    ''' </summary>
    ''' <remarks></remarks>
    Public Sub Paste()
        If Not (m_oWorksheet Is Nothing) Then
            m_oWorksheet.Paste()
        End If
    End Sub

    ''' <summary>
    ''' ��ǰѡ�������
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
    '�൱��ԭ����Worksheets("Sheet1").Range("A1:B3")���ش�A1��B3��Range
    'Worksheets("Sheet1").Range("A1")���ص�Ԫ��A1
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
    ''' �൱��ԭ��Worksheets(1).Range("C5:C10").Cells(1, 1)���ص�C5��C10��Range�еĵ�һ�е�һ�еĵ�Ԫ��(Ҳ�Ǳ�ʾΪRange)
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

    '����Range�ı߿�||�����³���(XlBordersIndex����)��   
    '                               xlDiagonalDown=5    �¶Խ���
    '                               xlDiagonalUp=6  �϶Խ���
    '                               xlEdgeBottom=4  �±߿�
    '                               xlEdgeLeft=1    ��߿�
    '                               xlEdgeRight=2   �ұ߿�
    '                               xlEdgeTop=3 �ϱ߿�
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
    ''' ������ɫ
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
    ''' �п�||��������������е��п���ȣ�ColumnWidth ���Է��ظÿ��ֵ����������е��п��ȣ������Է��� Null��
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
    ''' �и�||�԰�Ϊ��λ���ػ�����ָ�������������е��п���������еĸ��е��и߲��ȣ����� Null
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
    ''' �ϲ���Ԫ��||����ΪTrue���ϲ�������ȡ���ϲ�
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
    ''' ���ذ���ָ����Ԫ��(������Ԫ�񣬲���һƬ����)�ĺϲ��������ָ���ĵ�Ԫ���ںϲ������ڣ�������Է���ָ���ĵ�Ԫ��
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

    '���ػ����ú�����뷽ʽ||�����³���(XlHAlign����)�� 
    '                               xlHAlignCenter=3    ����
    '                               xlHAlignCenterAcrossSelection=7 ���о���
    '                               xlHAlignDistributed=8   ��ɢ���루������
    '                               xlHAlignFill=5  ���
    '                               xlHAlignGeneral=1   ����
    '                               xlHAlignJustify=6   ���˶���
    '                               xlHAlignLeft=2  ����������
    '                               xlHAlignRight=4 ���ң�������
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

    '���ػ�����������뷽ʽ||�����³���(XlVAlign����)�� 
    '                               xlVAlignCenter=2    ����
    '                               xlVAlignJustify=4   ���˶���
    '                               xlVAlignBottom=3    ����
    '                               xlVAlignDistributed=5   ��ɢ���� 
    '                               xlVAlignTop=1   ����
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

    '���Ƶ�ǰRange��������
    '����ճ�����裺
    'Range("A1:A2").Copy
    'Range("C3:H12").SelectRange
    'Worksheets(1).Paste
    Public Sub Copy()
        m_oRange.Copy()
    End Sub

    ''' <summary>
    ''' ѡ����ǰ��Range
    ''' </summary>
    ''' <remarks></remarks>
    Public Sub SelectRange()
        m_oRange.Select()
    End Sub

    Public Sub AutoFit()
        m_oRange.EntireColumn.AutoFit()
    End Sub

    ''' <summary>
    ''' ���ػ����õ�Ԫ��Ĺ�ʽ�������ֵ���ƣ�"=A4+A10" ���� "=$A$4+$A$10"
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
    ''' �滻
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
            oResult = 0 '��ɫ
            If Not (m_oFont Is Nothing) Then
                oResult = m_oFont.Color
            End If
            Return oResult
        End Get
    End Property
    Public Sub SetColor(ByVal R As Integer, ByVal G As Integer, ByVal B As Integer)
        Dim intRgb As Double '������Double����Integer�����ת��ǰ���ֵ��ͬ����Ϊm_oFont.Color������ʱ��Double����
        intRgb = Double.Parse(RGB(R, G, B))
        If Not (m_oFont Is Nothing) Then
            m_oFont.Color = intRgb
        End If
    End Sub

    ''' <summary>
    ''' ���ػ�����������ʽ||FontStyle = "Bold Italic" ���üӴֺ���б
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
    ''' ���ػ�������������
    ''' </summary>
    ''' <value></value>
    ''' <returns></returns>
    ''' <remarks></remarks>
    Public Property Name() As String
        Get
            Dim oResult As String
            oResult = "����"
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
    ''' ���ÿ�����
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
            oResult = 9 'Ĭ�Ͼź���
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
    ''' �����м��Ƿ���һ��ˮƽɾ����
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

    '�±�
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

    '�ϱ�
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

    '���ػ������»�������||�����³���(XlUnderlineStyle����)��   
    '                               xlUnderlineStyleNone=1  ���»��� 
    '                               xlUnderlineStyleSingle=2    ���»���
    '                               xlUnderlineStyleDouble=3    ˫�»���
    '                               xlUnderlineStyleSingleAccounting=4  ����õ��»���
    '                               xlUnderlineStyleDoubleAccounting=5  �����˫�»���
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
        Dim intRgb As Double '������Double����Integer�����ת��ǰ���ֵ��ͬ����Ϊm_oFont.Color������ʱ��Double����
        intRgb = Double.Parse(RGB(R, G, B))
        If Not (m_oBorder Is Nothing) Then
            m_oBorder.Color = intRgb
        End If
    End Sub

    '���ػ����ñ߿������||�����³���(XlLineStyle����)��{0-13}   
    '                               xlContinuous=1  ������
    '                               xlDash=3    ������
    '                               xlDashDot=4 �ߵ�����
    '                               xlDashDotDot=5  �ߵ������
    '                               xlDot=2 ������
    '                               xlDouble=9  ˫������
    '                               xlSlantDashDot=6    ��б���ߵ�����
    '                               xlLineStyleNone=0   ��
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

    '���ػ����ñ߿�Ĵ�ϸ||�����³���(XlBorderWeight����)��   
    '                               xlHairline=1    ��ϸ 
    '                               xlThin=2    ϸ
    '                               xlMedium=3  �е�
    '                               xlThick=4   ��
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
'Class ExcelInterior    ��ʾ������ڲ�
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
        Dim intRgb As Double '������Double����Integer�����ת��ǰ���ֵ��ͬ����Ϊm_oFont.Color������ʱ��Double����
        intRgb = Double.Parse(RGB(R, G, B))
        If Not (m_oInterior Is Nothing) Then
            m_oInterior.Color = intRgb
        End If
    End Sub
End Class