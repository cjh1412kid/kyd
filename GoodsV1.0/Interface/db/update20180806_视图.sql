U S E   [ Y H S m a r t R e t a i l ]  
 G O  
 / * * * * * *   O b j e c t :     V i e w   [ d b o ] . [ Y H S R _ G o o d s _ V i e w ]         S c r i p t   D a t e :   0 8 / 0 6 / 2 0 1 8   1 9 : 2 9 : 4 9   * * * * * * /  
 S E T   A N S I _ N U L L S   O N  
 G O  
 S E T   Q U O T E D _ I D E N T I F I E R   O N  
 G O  
 C R E A T E   V I E W   [ d b o ] . [ Y H S R _ G o o d s _ V i e w ]   A S    
 S E L E C T    
 	 G . * ,    
 	 S X 1 . S X N a m e   A S   S X 1 N a m e ,   S X 1 . V a l u e   A S   S X 1 V a l u e ,  
 	 S X 2 . S X N a m e   A S   S X 2 N a m e ,   S X 2 . V a l u e   A S   S X 2 V a l u e ,  
 	 S X 3 . S X N a m e   A S   S X 3 N a m e ,   S X 3 . V a l u e   A S   S X 3 V a l u e ,  
 	 S X 4 . S X N a m e   A S   S X 4 N a m e ,   S X 4 . V a l u e   A S   S X 4 V a l u e ,  
 	 S X 5 . S X N a m e   A S   S X 5 N a m e ,   S X 5 . V a l u e   A S   S X 5 V a l u e ,  
 	 S X 6 . S X N a m e   A S   S X 6 N a m e ,   S X 6 . V a l u e   A S   S X 6 V a l u e ,  
 	 S X 7 . S X N a m e   A S   S X 7 N a m e ,   S X 7 . V a l u e   A S   S X 7 V a l u e ,  
 	 S X 8 . S X N a m e   A S   S X 8 N a m e ,   S X 8 . V a l u e   A S   S X 8 V a l u e ,  
 	 S X 9 . S X N a m e   A S   S X 9 N a m e ,   S X 9 . V a l u e   A S   S X 9 V a l u e ,  
 	 S X 1 0 . S X N a m e   A S   S X 1 0 N a m e ,   S X 1 0 . V a l u e   A S   S X 1 0 V a l u e ,  
 	 S X 1 1 . S X N a m e   A S   S X 1 1 N a m e ,   S X 1 1 . V a l u e   A S   S X 1 1 V a l u e ,  
 	 S X 1 2 . S X N a m e   A S   S X 1 2 N a m e ,   S X 1 2 . V a l u e   A S   S X 1 2 V a l u e ,  
 	 S X 1 3 . S X N a m e   A S   S X 1 3 N a m e ,   S X 1 3 . V a l u e   A S   S X 1 3 V a l u e ,  
 	 S X 1 4 . S X N a m e   A S   S X 1 4 N a m e ,   S X 1 4 . V a l u e   A S   S X 1 4 V a l u e ,  
 	 S X 1 5 . S X N a m e   A S   S X 1 5 N a m e ,   S X 1 5 . V a l u e   A S   S X 1 5 V a l u e ,  
 	 S X 1 6 . S X N a m e   A S   S X 1 6 N a m e ,   S X 1 6 . V a l u e   A S   S X 1 6 V a l u e ,  
 	 S X 1 7 . S X N a m e   A S   S X 1 7 N a m e ,   S X 1 7 . V a l u e   A S   S X 1 7 V a l u e ,  
 	 S X 1 8 . S X N a m e   A S   S X 1 8 N a m e ,   S X 1 8 . V a l u e   A S   S X 1 8 V a l u e ,  
 	 S X 1 9 . S X N a m e   A S   S X 1 9 N a m e ,   S X 1 9 . V a l u e   A S   S X 1 9 V a l u e ,  
 	 S X 2 0 . S X N a m e   A S   S X 2 0 N a m e ,   S X 2 0 . V a l u e   A S   S X 2 0 V a l u e  
 F R O M   Y H S R _ G o o d s _ S h o e s   G  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 1 '  
 )   S X 1   O N   G . C o m p a n y _ S e q   =   S X 1 . C o m p a n y _ S e q   A N D   G . S X 1   =   S X 1 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 2 '  
 )   S X 2   O N   G . C o m p a n y _ S e q   =   S X 2 . C o m p a n y _ S e q   A N D   G . S X 2   =   S X 2 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 3 '  
 )   S X 3   O N   G . C o m p a n y _ S e q   =   S X 3 . C o m p a n y _ S e q   A N D   G . S X 3   =   S X 3 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 4 '  
 )   S X 4   O N   G . C o m p a n y _ S e q   =   S X 4 . C o m p a n y _ S e q   A N D   G . S X 4   =   S X 4 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 5 '  
 )   S X 5   O N   G . C o m p a n y _ S e q   =   S X 5 . C o m p a n y _ S e q   A N D   G . S X 5   =   S X 5 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 6 '  
 )   S X 6   O N   G . C o m p a n y _ S e q   =   S X 6 . C o m p a n y _ S e q   A N D   G . S X 6   =   S X 6 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 7 '  
 )   S X 7   O N   G . C o m p a n y _ S e q   =   S X 7 . C o m p a n y _ S e q   A N D   G . S X 7   =   S X 7 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 8 '  
 )   S X 8   O N   G . C o m p a n y _ S e q   =   S X 8 . C o m p a n y _ S e q   A N D   G . S X 8   =   S X 8 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 9 '  
 )   S X 9   O N   G . C o m p a n y _ S e q   =   S X 9 . C o m p a n y _ S e q   A N D   G . S X 9   =   S X 9 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 1 0 '  
 )   S X 1 0   O N   G . C o m p a n y _ S e q   =   S X 1 0 . C o m p a n y _ S e q   A N D   G . S X 1 0   =   S X 1 0 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 1 1 '  
 )   S X 1 1   O N   G . C o m p a n y _ S e q   =   S X 1 1 . C o m p a n y _ S e q   A N D   G . S X 1 1   =   S X 1 1 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 1 2 '  
 )   S X 1 2   O N   G . C o m p a n y _ S e q   =   S X 1 2 . C o m p a n y _ S e q   A N D   G . S X 1 2   =   S X 1 2 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 1 3 '  
 )   S X 1 3   O N   G . C o m p a n y _ S e q   =   S X 1 3 . C o m p a n y _ S e q   A N D   G . S X 1 3   =   S X 1 3 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 1 4 '  
 )   S X 1 4   O N   G . C o m p a n y _ S e q   =   S X 1 4 . C o m p a n y _ S e q   A N D   G . S X 1 4   =   S X 1 4 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 1 5 '  
 )   S X 1 5   O N   G . C o m p a n y _ S e q   =   S X 1 5 . C o m p a n y _ S e q   A N D   G . S X 1 5   =   S X 1 5 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 1 6 '  
 )   S X 1 6   O N   G . C o m p a n y _ S e q   =   S X 1 6 . C o m p a n y _ S e q   A N D   G . S X 1 6   =   S X 1 6 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 1 7 '  
 )   S X 1 7   O N   G . C o m p a n y _ S e q   =   S X 1 7 . C o m p a n y _ S e q   A N D   G . S X 1 7   =   S X 1 7 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 1 8 '  
 )   S X 1 8   O N   G . C o m p a n y _ S e q   =   S X 1 8 . C o m p a n y _ S e q   A N D   G . S X 1 8   =   S X 1 8 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 1 9 '  
 )   S X 1 9   O N   G . C o m p a n y _ S e q   =   S X 1 9 . C o m p a n y _ S e q   A N D   G . S X 1 9   =   S X 1 9 . C o d e  
  
 L E F T   J O I N   (  
 	 	 S E L E C T    
 	 	 	 A . C o m p a n y _ S e q ,   A . S X I D ,   A . S X N a m e ,   B . C o d e ,   B . V a l u e  
 	 	 F R O M    
 	 	 	 [ d b o ] . [ Y H S R _ G o o d s _ S X ]   A    
 	 	 L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ S X _ O p t i o n ]   B   O N   A . S e q   =   B . S X _ S e q   A N D   B . D e l   =   0    
 	 	 W H E R E    
 	 	 	 A . D e l   =   0   A N D   A . S X I D   =   ' S X 2 0 '  
 )   S X 2 0   O N   G . C o m p a n y _ S e q   =   S X 2 0 . C o m p a n y _ S e q   A N D   G . S X 2 0   =   S X 2 0 . C o d e  
 G O  
 