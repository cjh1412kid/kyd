/ *  
 N a v i c a t   S Q L   S e r v e r   D a t a   T r a n s f e r  
  
 S o u r c e   S e r v e r                   :   1 9 2 . 1 6 8 . 2 . 1 4 6  
 S o u r c e   S e r v e r   V e r s i o n   :   1 0 0 0 0 0  
 S o u r c e   H o s t                       :   1 9 2 . 1 6 8 . 2 . 1 4 6 : 1 4 3 3  
 S o u r c e   D a t a b a s e               :   Y H S m a r t R e t a i l  
 S o u r c e   S c h e m a                   :   d b o  
  
 T a r g e t   S e r v e r   T y p e         :   S Q L   S e r v e r  
 T a r g e t   S e r v e r   V e r s i o n   :   1 0 0 0 0 0  
 F i l e   E n c o d i n g                   :   6 5 0 0 1  
  
 D a t e :   2 0 1 8 - 0 8 - 0 8   1 5 : 1 4 : 2 3  
 * /  
  
  
 - -   - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
 - -   T a b l e   s t r u c t u r e   f o r   Y H S R _ G o o d s _ S X _ O p t i o n  
 - -   - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
 D R O P   T A B L E   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]  
 G O  
 C R E A T E   T A B L E   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   (  
 [ S e q ]   i n t   N O T   N U L L   I D E N T I T Y ( 1 , 1 )   ,  
 [ S X _ S e q ]   i n t   N O T   N U L L   ,  
 [ C o d e ]   v a r c h a r ( 1 0 )   N O T   N U L L   ,  
 [ V a l u e ]   v a r c h a r ( 5 0 )   N O T   N U L L   ,  
 [ I n p u t T i m e ]   d a t e t i m e   N O T   N U L L   D E F A U L T   ( g e t d a t e ( ) )   ,  
 [ D e l ]   i n t   N O T   N U L L   D E F A U L T   ( ( 0 ) )    
 )  
  
  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S X _ O p t i o n ' ,    
 ' C O L U M N ' ,   N ' S e q ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' �^�S( ;N.�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' S e q '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' �^�S( ;N.�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' S e q '  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S X _ O p t i o n ' ,    
 ' C O L U M N ' ,   N ' S X _ S e q ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' ^\'`�^�S( Y.�: Y H S R _ G o o d s _ S X h�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' S X _ S e q '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' ^\'`�^�S( Y.�: Y H S R _ G o o d s _ S X h�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' S X _ S e q '  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S X _ O p t i o n ' ,    
 ' C O L U M N ' ,   N ' C o d e ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' ^\'`x'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' C o d e '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' ^\'`x'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' C o d e '  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S X _ O p t i o n ' ,    
 ' C O L U M N ' ,   N ' V a l u e ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' ^\'`<P'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' V a l u e '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' ^\'`<P'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' V a l u e '  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S X _ O p t i o n ' ,    
 ' C O L U M N ' ,   N ' I n p u t T i m e ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' R�^�e��'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' I n p u t T i m e '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N ' R�^�e��'  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' I n p u t T i m e '  
 G O  
 I F   ( ( S E L E C T   C O U N T ( * )   f r o m   f n _ l i s t e x t e n d e d p r o p e r t y ( ' M S _ D e s c r i p t i o n ' ,    
 ' S C H E M A ' ,   N ' d b o ' ,    
 ' T A B L E ' ,   N ' Y H S R _ G o o d s _ S X _ O p t i o n ' ,    
 ' C O L U M N ' ,   N ' D e l ' ) )   >   0 )    
 E X E C   s p _ u p d a t e e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N '  Rd�hƋ( 0 : *g Rd�, 1 : �] Rd�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' D e l '  
 E L S E  
 E X E C   s p _ a d d e x t e n d e d p r o p e r t y   @ n a m e   =   N ' M S _ D e s c r i p t i o n ' ,   @ v a l u e   =   N '  Rd�hƋ( 0 : *g Rd�, 1 : �] Rd�) '  
 ,   @ l e v e l 0 t y p e   =   ' S C H E M A ' ,   @ l e v e l 0 n a m e   =   N ' d b o '  
 ,   @ l e v e l 1 t y p e   =   ' T A B L E ' ,   @ l e v e l 1 n a m e   =   N ' Y H S R _ G o o d s _ S X _ O p t i o n '  
 ,   @ l e v e l 2 t y p e   =   ' C O L U M N ' ,   @ l e v e l 2 n a m e   =   N ' D e l '  
 G O  
  
 - -   - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
 - -   I n d e x e s   s t r u c t u r e   f o r   t a b l e   Y H S R _ G o o d s _ S X _ O p t i o n  
 - -   - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
  
 - -   - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
 - -   P r i m a r y   K e y   s t r u c t u r e   f o r   t a b l e   Y H S R _ G o o d s _ S X _ O p t i o n  
 - -   - - - - - - - - - - - - - - - - - - - - - - - - - - - -  
 A L T E R   T A B L E   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   A D D   P R I M A R Y   K E Y   ( [ S e q ] )  
 G O  
 