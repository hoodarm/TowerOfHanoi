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
    // code to be written to replace this feeble attempt
    Rod intermediate = Rod.third (from, to); // find intermediate rod
    toh.move (from, intermediate); // move top disc to intermediate
    toh.move (from, to); // move next disc to target
    toh.move (from, to); // move next disc to target
  }
  
  public static void main (String[] args) 
  {
    // construct a new tower of Hanoi with 5 discs
    TowerOfHanoi toh = new TowerOfHanoi (5);
    
    try
    {
      // task is to move all discs from rod A to rod C
      move (toh, toh.getNumDiscs (), Rod.A, Rod.C);
    }
    catch (IllegalTowerOfHanoiMoveException e)
    {
      System.out.println ("illegal move: " + e);
    }
    
    ui = new TOHUserInterface (toh);
    TOHUserInterface.start ();
  }

  private static TOHUserInterface ui;
}
