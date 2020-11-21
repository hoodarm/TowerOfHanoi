// Copyright (C) 2020 Jarmo Hurri

// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

public class TOHApp
{
  static void move (TowerOfHanoi toh, int numDiscs, Rod from, Rod to)
    throws IllegalTowerOfHanoiMoveException
  {
    Rod intermediate;
    if (from != Rod.A && to != Rod.A)
      intermediate = Rod.A;
    else if (from != Rod.B && to != Rod.B)
      intermediate = Rod.B;
    else
      intermediate = Rod.C;

    // code to be written to replace this feeble attempt
    toh.move (from, intermediate);
    toh.move (from, to);
  }
  
  public static void main (String[] args) 
  {
    TowerOfHanoi toh = new TowerOfHanoi (5);
    
    try
    {
      move (toh, toh.getNumDiscs (), Rod.A, Rod.C);
    }
    catch (IllegalTowerOfHanoiMoveException e)
    {
      System.out.println ("illegal move: " + e);
    }
    
    ui = new TOHUserInterface (toh);
    ui.start ();
  }

  private static TOHUserInterface ui;
}
