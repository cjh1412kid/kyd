D R O P   T A B L E   [ d b o ] . [ Y H S R _ G o o d s _ S i z e ]  
 G O  
 C R E A T E   T A B L E   [ d b o ] . [ Y H S R _ G o o d s _ S i z e ]   (  
 [ S e q ]   i n t   N O T   N U L L   I D E N T I T Y ( 1 , 1 )   ,  
 [ C o m p a n y _ S e q ]   i n t   N O T   N U L L   ,  
 [ S i z e C o d e ]   v a r c h a r ( 5 0 )   N U L L   ,  
 [ S i z e N a m e ]   v a r c h a r ( 5 0 )   N O T   N U L L   ,  
 [ I n p u t T i m e ]   d a t e t i m e   N O T   N U L L   D E F A U L T   ( g e t d a t e ( ) )   ,  
 [ D e l ]   i n t   N O T   N U L L   D E F A U L T   ( ( 0 ) )    
 )  
  
  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S i z e ' ,    
 ' C O L U M N ' ,   N ' S e q ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' �^�S( ;N.�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' S e q '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' �^�S( ;N.�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' S e q '  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S i z e ' ,    
 ' C O L U M N ' ,   N ' C o m p a n y _ S e q ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' lQ�S�^�S( Y.�: Y H S R _ B a s e _ C o m p a n y h�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' C o m p a n y _ S e q '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' lQ�S�^�S( Y.�: Y H S R _ B a s e _ C o m p a n y h�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' C o m p a n y _ S e q '  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S i z e ' ,    
 ' C O L U M N ' ,   N ' S i z e C o d e ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' :\xx'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' S i z e C o d e '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' :\xx'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' S i z e C o d e '  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S i z e ' ,    
 ' C O L U M N ' ,   N ' S i z e N a m e ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' :\xT�y'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' S i z e N a m e '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' :\xT�y'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' S i z e N a m e '  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S i z e ' ,    
 ' C O L U M N ' ,   N ' I n p u t T i m e ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' R�^�e��'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' I n p u t T i m e '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' R�^�e��'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' I n p u t T i m e '  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S i z e ' ,    
 ' C O L U M N ' ,   N ' D e l ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N '  Rd�hƋ( 0 : *g Rd�, 1 : �] Rd�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' D e l '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N '  Rd�hƋ( 0 : *g Rd�, 1 : �] Rd�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S i z e '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' D e l '  
 G O  
  
 - -   - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
 - -   I n d e x e s   s t r u c t u r e   f o r   t a b l e   Y H S R _ G o o d s _ S i z e  
 - -   - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
  
 - -   - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
 - -   P r i m a r y   K e y   s t r u c t u r e   f o r   t a b l e   Y H S R _ G o o d s _ S i z e  
 - -   - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
 A L T E R   T A B L E   [ d b o ] . [ Y H S R _ G o o d s _ S i z e ]   A D D   P R I M A R Y   K E Y   ( [ S e q ] )  
 G O  
 