USE [master]
GO
/****** Object:  Database [ESmart]    Script Date: 2018/8/21 11:11:09 ******/
CREATE DATABASE [ESmart] ON  PRIMARY 
( NAME = N'ESmart', FILENAME = N'D:\MSSqlServer\OrderSales\ESmart.mdf' , SIZE = 2304KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'ESmart_log', FILENAME = N'D:\MSSqlServer\OrderSales\ESmart_log.ldf' , SIZE = 768KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [ESmart] SET COMPATIBILITY_LEVEL = 100
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ESmart].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ESmart] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ESmart] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ESmart] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ESmart] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ESmart] SET ARITHABORT OFF 
GO
ALTER DATABASE [ESmart] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ESmart] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ESmart] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ESmart] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ESmart] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ESmart] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ESmart] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ESmart] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ESmart] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ESmart] SET  ENABLE_BROKER 
GO
ALTER DATABASE [ESmart] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ESmart] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ESmart] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ESmart] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ESmart] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ESmart] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ESmart] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ESmart] SET RECOVERY FULL 
GO
ALTER DATABASE [ESmart] SET  MULTI_USER 
GO
ALTER DATABASE [ESmart] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ESmart] SET DB_CHAINING OFF 
GO
EXEC sys.sp_db_vardecimal_storage_format N'ESmart', N'ON'
GO
USE [ESmart]
GO
/****** Object:  Table [dbo].[platform]    Script Date: 2018/8/21 11:11:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[platform](
	[Seq] [int] IDENTITY(1,1) NOT NULL,
	[PlatKey] [varchar](255) NOT NULL,
	[PlatSecret] [varchar](255) NOT NULL,
	[ErpBrand] [varchar](120) NULL,
	[ErpType] [varchar](64) NULL,
	[ErpVersion] [varchar](32) NULL,
	[Company] [varchar](255) NULL,
	[InputTime] [datetime] NOT NULL,
	[Del] [varchar](255) NOT NULL,
	[BeanName] [varchar](64) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Seq] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[platform] ADD  DEFAULT (getdate()) FOR [InputTime]
GO
ALTER TABLE [dbo].[platform] ADD  DEFAULT ((0)) FOR [Del]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'平台的调用key' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'platform', @level2type=N'COLUMN',@level2name=N'PlatKey'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'平台的调用secret' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'platform', @level2type=N'COLUMN',@level2name=N'PlatSecret'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'erp的使用品牌' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'platform', @level2type=N'COLUMN',@level2name=N'ErpBrand'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'传统erp，电商erp' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'platform', @level2type=N'COLUMN',@level2name=N'ErpType'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'erp的对应版本' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'platform', @level2type=N'COLUMN',@level2name=N'ErpVersion'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'erp的使用公司名称' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'platform', @level2type=N'COLUMN',@level2name=N'Company'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'platform创建是时间' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'platform', @level2type=N'COLUMN',@level2name=N'InputTime'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'platform是否删除' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'platform', @level2type=N'COLUMN',@level2name=N'Del'
GO
USE [master]
GO
ALTER DATABASE [ESmart] SET  READ_WRITE 
GO
