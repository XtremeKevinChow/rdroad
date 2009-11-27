//*******************************************
// ** Description:  Data Access Object for ItemImage
// ** Author     :  Code generator
// ** Created    :   2008-6-30 20:22:21
// ** Modified   :
//*******************************************

namespace Magic.Basis
{
	using System;
	using Magic.Framework.Data;
	using Magic.Framework.ORM;
	using Magic.Framework.ORM.Mapping;

	/// <summary>
	/// Data Access Object for ItemImage
	/// 商品图片
	/// </summary>
	[Table("PRD_ITEM_IMAGE")]
	public partial class  ItemImage : IEntity
	{
		#region Private Fields
		private int _imageID;
		private int _itemID;
		private string _colorCode;
		private int _imageType;
		private string _imageBig;
		private string _imageMidium;
		private string _imageSmall;
        private string _imageOriginal;
        private bool _isDefault;
		#endregion

		#region Public Properties
        [Column(Name = "IMG_ID", DbType = StdDbType.Int32, IsPrimary = true, IsSequence = true, SequenceName = "SEQ_PRD_ITEM_IMAGE")]
		public int ImageID
		{
			get { return this._imageID; }
			set { this._imageID = value; }
		}

		[Column(Name = "ITM_ID", DbType = StdDbType.Int32)]
		public int ItemID
		{
			get { return this._itemID; }
			set { this._itemID = value; }
		}

		[Column(Name = "COLOR_CODE", DbType = StdDbType.AnsiString, Length = 6)]
		public string ColorCode
		{
			get { return this._colorCode; }
			set { this._colorCode = value; }
		}

		///<summary>
		/// 1: 产品主图
		/// 2: 侧面图片
		/// 3: 背后图片
		/// 4: 模特图片
		/// 5: 面料小图片
		///</summary>
		[Column(Name = "IMG_TYPE", DbType = StdDbType.Int32)]
		public int ImageType
		{
			get { return this._imageType; }
			set { this._imageType = value; }
		}

		[Column(Name = "IMG_BIG", DbType = StdDbType.AnsiString, Length = 100)]
		public string ImageBig
		{
			get { return this._imageBig; }
			set { this._imageBig = value; }
		}

        [Column(Name = "IMG_MID", DbType = StdDbType.AnsiString, Length = 100)]
		public string ImageMidium
		{
			get { return this._imageMidium; }
			set { this._imageMidium = value; }
		}

        [Column(Name = "IMG_SMALL", DbType = StdDbType.AnsiString, Length = 100)]
		public string ImageSmall
		{
			get { return this._imageSmall; }
			set { this._imageSmall = value; }
		}

        [Column(Name = "IMAGE_ORIGIAL", DbType = StdDbType.AnsiString, Length = 100)]
        public string ImageOriginal
        {
            get { return this._imageOriginal; }
            set { this._imageOriginal = value; }
        }

        [Column(Name = "IS_DEFAULT", DbType = StdDbType.Bool)]
        public bool IsDefault
        {
            get { return this._isDefault; }
            set { this._isDefault = value; }
        }
		#endregion

		#region Entity Methods
		public ItemImage()
		{
		}

		public bool Create(ISession session)
		{
			return EntityManager.Create(session, this);
		}
		public bool Update(ISession session)
		{
			return EntityManager.Update(session, this);
		}
		public bool Update(ISession session, params string[] propertyNames2Update)
		{
			return EntityManager.Update(session, this, propertyNames2Update);
		}
		public bool Delete(ISession session)
		{
			return EntityManager.Delete(session, this);
		}
		public static ItemImage Retrieve(ISession session, decimal imageID)
		{
			return EntityManager.Retrieve<ItemImage>(session, imageID);
		}
		public static bool Delete(ISession session, decimal imageID)
		{
			return EntityManager.Delete<ItemImage>(session, imageID);
		}
		#endregion
	}
}
