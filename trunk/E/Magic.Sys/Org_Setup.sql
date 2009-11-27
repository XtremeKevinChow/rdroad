drop table SYS_Org;
CREATE TABLE [dbo].[SYS_Org](
	[Org_ID] [int] IDENTITY(1,1) NOT NULL,
	[PARENT_ID] [int] NULL,
	[Org_Code] [varchar](15) NULL,
	[Org_Name] [nvarchar](20) NULL,
	[Org_Type] [int] NULL,
	[IS_VIRTUAL] [bit] NULL,
	[IS_ROOT] [bit] NULL,
	[Org_Seq] [smallint] NULL,
	[DEL_FLAG] [bit] NULL,
	[Description] [nvarchar](50) NULL,
	[Manager] [int] NULL,
	[Create_Date] [datetime] NULL,
	[Create_By] [int] NULL,
	[Modify_Date] [datetime] NULL,
	[Modify_By] [int] NULL,
	 CONSTRAINT [PK_SYS_Org] PRIMARY KEY CLUSTERED 
	(
		[Org_ID] ASC
	)ON [PRIMARY]
) ON [PRIMARY];

drop table SYS_Partner;
CREATE TABLE [dbo].[SYS_Partner](
	[Org_ID] [int] NOT NULL,
	[Short_Name] [nvarchar](20) NULL,
	[Address] [nvarchar](250) NULL,
	[Contact] [nvarchar](50) NULL,
	[Contact_Phone] [nvarchar](25) NULL,
	[Contact_Mobile] [nvarchar](25) NULL,
	[Ship_Address] [nvarchar](250) NULL,
	[Zip_Code] [nvarchar](10) NULL,
	[Balance_Request] [nvarchar](500) NULL DEFAULT (''),
	[Balance_Period] [int] NULL,
	[Our_Contact] [int] NULL,
	[Our_ChargeOf] [int] NULL,
	[ChargeOf] [nvarchar](50) NULL,
	[Shipman] [nvarchar](50) NULL,
	[Web_Site] [nvarchar](255) NULL,
	[Email] [nvarchar](50) NULL,
	[Phone] [nvarchar](25) NULL,
	[Status] [smallint] NULL DEFAULT ((0)),
	CONSTRAINT [PK_SYS_Partner] PRIMARY KEY CLUSTERED 
	(
		[Org_ID] ASC
	)ON [PRIMARY]
) ON [PRIMARY];

drop table SYS_Org_Partner;
CREATE TABLE [dbo].[SYS_Org_Partner](
	[Org_ID] [int] NOT NULL,
	[Partner_ID] [int] NOT NULL,
	CONSTRAINT [PK_SYS_Org_Partner] PRIMARY KEY CLUSTERED 
	(
		[Org_ID] ASC,
		[Partner_ID] ASC
	) ON [PRIMARY]
) ON [PRIMARY];

drop table SYS_Org_View;
CREATE TABLE [dbo].[SYS_Org_View](
	[Parent_Id] [int] NOT NULL,
	[Child_Id] [int] NOT NULL,
	[Flag] [bit] NOT NULL,
	[Level] [int] NOT NULL,
	[Trigger_Id] [int] NOT NULL
) ON [PRIMARY];
CREATE NONCLUSTERED INDEX [IX_SYS_Org_View_Child] ON [dbo].[SYS_Org_View] 
(
	[Child_Id] ASC
)ON [PRIMARY];
CREATE CLUSTERED INDEX [IX_SYS_Org_View_Parent] ON [dbo].[SYS_Org_View] 
(
	[Parent_Id] ASC
)ON [PRIMARY];

CREATE TRIGGER [dbo].[SYS_Org_Insert_Delete] 
   ON  [dbo].[SYS_Org]
   AFTER INSERT,DELETE
AS 
BEGIN
	SET NOCOUNT ON;
	--for inserted
	if (select count(*) from inserted)>0 begin
		insert into SYS_Org_View(Parent_Id,Child_Id,Flag,Trigger_Id,[Level])
			select b.PARENT_ID,a.Org_ID,1,-1,-1
			from inserted a
			inner join SYS_Org_View b on a.PARENT_ID=b.Child_Id;
		insert into SYS_Org_View(Parent_Id,Child_Id,Flag,Trigger_Id,[Level])
			select Org_ID,Org_ID,1,-1,-1 from inserted where IS_VIRTUAL is null or IS_VIRTUAL=0;
		update SYS_Org_View set [Level]=a.cnt
			from(
				select t1.Org_ID,count(t2.Parent_Id) as cnt
				from inserted t1,SYS_Org_View t2
				where t1.Org_ID=t2.Child_Id and t2.[Level]=-1
				group by t1.Org_ID
			)a
			where a.Org_ID=Child_Id
	end;
	--for deleted
	if (select count(*) from deleted)>0 begin
		delete from dbo.SYS_Org_View where 
			Parent_Id in (select Org_ID from deleted)
			or
			Child_Id in (select Org_ID from deleted);
	end;
END;

CREATE TRIGGER [dbo].[SYS_Org_Update] 
   ON  [dbo].[SYS_Org]
   AFTER UPDATE
AS 
BEGIN
	SET NOCOUNT ON;
	--for updated
	if update(DEL_FLAG) begin
		update SYS_Org_View set Flag=0,Trigger_Id=a.Parent_Id
			from (
				select distinct * from SYS_Org_View 
				where Parent_Id in(select Org_ID from inserted where DEL_FLAG=1)
			) a
			where SYS_Org_View.Trigger_Id=-1
				and (SYS_Org_View.Parent_Id=a.Child_Id or SYS_Org_View.Child_Id=a.Child_Id);
		update SYS_Org_View set Flag=1,Trigger_Id=-1
			from (
				select distinct * from SYS_Org_View 
				where Parent_Id in(select Org_ID from inserted where DEL_FLAG=0)
			) a
			where SYS_Org_View.Trigger_Id=a.Parent_Id
				and (SYS_Org_View.Parent_Id=a.Child_Id or SYS_Org_View.Child_Id=a.Child_Id);
	end;
END;

set identity_insert SYS_Org on;
INSERT INTO [SYS_Org](Org_Id,[PARENT_ID],[Org_Code],[Org_Name],[Org_Type],[IS_VIRTUAL],[IS_ROOT],[Org_Seq],[DEL_FLAG],[Description])
VALUES(1,-1,'1000',N'上海汇申网络信息有限公司',1,0,1,1,0,N'上海汇申网络信息有限公司');
INSERT INTO [SYS_Org](Org_Id,[PARENT_ID],[Org_Code],[Org_Name],[Org_Type],[IS_VIRTUAL],[IS_ROOT],[Org_Seq],[DEL_FLAG],[Description])
VALUES(2,-1,'0000',N'合作企业',2,1,1,1,0,N'合作企业');
set identity_insert SYS_Org off;