import java.util.*;

public class MAV2 {

    static void Menu() {
        System.out.println(" -------------------MENU-------------------");
        System.out.println(" |  [1] Choose Allocation Strategy        |");
        System.out.println(" |  [2] Set Memory Partitions             |");
        System.out.println(" |  [3] Add Process                       |");
        System.out.println(" |  [4] Deallocate Process                |");
        System.out.println(" |  [5] View Memory Status                |");
        System.out.println(" |  [6] Exit                              |");
        System.out.println(" ------------------------------------------");
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int MAX_PARTITIONS = 100;
        int[] partition = new int[MAX_PARTITIONS];
        int[] available = new int[MAX_PARTITIONS];
        String[] Pname = new String[MAX_PARTITIONS];
        int[] JobSize = new int[MAX_PARTITIONS];
        String[] newName = new String[MAX_PARTITIONS];
        int[] newSize = new int[MAX_PARTITIONS];

        int a, PartNum = 0, allocStrat = 0, choice = 0;

        System.out.println("==============================================================");
        System.out.println("                MEMORY ALLOCATION VISUALIZER");
        System.out.println("==============================================================");
        System.out.println("Group Number: 7");
        System.out.println("Section     : BSIT 2-1");
        System.out.println("Members     :");
        System.out.println("   <> Belizar, John Wayne I.");
        System.out.println("   <> Bustamante, Angel Joy R.");
        System.out.println("   <> Delos Reyes, Donna Marie J.");
        System.out.println("   <> Sagmit, Bodee Ryan Z.");
        System.out.println("==============================================================");
        System.out.println("Welcome to the Memory Allocation Program!");
        System.out.println("Press Enter to continue...");
        in.nextLine();
        System.out.println();

        try {
            do {
                Menu();

                //VALIDATE CHOICE INPUT 
                while (true) {
                    System.out.print("Enter your choice (1-6): ");
                    if (in.hasNextInt()) {
                        choice = in.nextInt();
                        in.nextLine();
                        if (choice >= 1 && choice <= 6) break;
                        else System.out.println("Invalid input. Please enter a number between 1 and 6.");
                    } else {
                        System.out.println("Invalid input. Please enter a number.");
                        in.nextLine(); // clear buffer
                    }
                }

                switch (choice) {
                    case 1: // Choose allocation strategy
                        System.out.println("  --------Memory Allocation Strategies-------");
                        System.out.println("  |  [1] First Fit                          |");
                        System.out.println("  |  [2] Best Fit                           |");
                        System.out.println("  -------------------------------------------");

                        while (true) {
                            System.out.print("Select Allocation Strategy (1 or 2): ");
                            if (in.hasNextInt()) {
                                allocStrat = in.nextInt();
                                in.nextLine();
                                if (allocStrat == 1) {
                                    System.out.println("Allocation strategy set to: First Fit");
                                    break;
                                } else if (allocStrat == 2) {
                                    System.out.println("Allocation strategy set to: Best Fit");
                                    break;
                                } else {
                                    System.out.println("Invalid input. Please enter 1 or 2.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid number (1 or 2).");
                                in.nextLine();
                            }
                        }
                        break;

                    case 2: // Set partitions
                        while (true) {
                            System.out.print("Enter number of partitions: ");
                            if (in.hasNextInt()) {
                                PartNum = in.nextInt();
                                if (PartNum > 0 && PartNum <= MAX_PARTITIONS) {
                                    break;
                                } else {
                                    System.out.println("Invalid number. Enter between 1 and " + MAX_PARTITIONS + ".");
                                }     
                            } else {
                                System.out.println("Invalid input. Please enter a valid integer.");
                                in.nextLine();
                            }
                        }

                        for (a = 0; a < PartNum; a++) {
                            while (true) {
                                System.out.print("Enter size of partition " + (a + 1) + ": ");
                                if (in.hasNextInt()) {
                                    int size = in.nextInt();
                                    if (size > 0){
                                        partition[a] = size;
                                        available[a] = size;
                                        Pname[a] = null;
                                        JobSize[a] = 0;
                                        in.nextLine();
                                        break;
                                    } else {
                                        System.out.println("Size must be greater than 0.");
                                    }
                                } else {
                                    System.out.println("Invalid input. Please enter a positive integer.");
                                }
                            }
                        }

                        System.out.println("Memory partitions set successfully!");
                        System.out.println("------------------------------------");
                        System.out.printf("| %-10s | %-18s \n", "Partition", "Memory Block Size");
                        System.out.println("------------------------------------");
                        for (int i = 0; i < PartNum; i++) {
                            System.out.printf("| %-10d | %6dK%-10s \n", (i + 1), partition[i], "");
                        }
                        System.out.println("------------------------------------");
                        break;

                    case 3: // Add process
                        if (PartNum == 0) {
                            System.out.println("Please set memory partitions first using option [2].");
                            break;
                        }
                        if (allocStrat == 0) {
                            System.out.println("Please choose an allocation strategy first using option [1].");
                            break;
                        }

                        for (int p = 0; p < PartNum; p++) {
                            System.out.print("Enter process " + (p + 1) + " name: ");
                            newName[p] = in.nextLine();

                            int size;
                            while (true) {
                                System.out.print("Enter process " + (p + 1) + " size: ");
                                if (in.hasNextInt()) {
                                    size = in.nextInt();
                                    in.nextLine();
                                    if (size > 0) {
                                        newSize[p] = size;
                                        break;
                                    } else {
                                        System.out.println("Size must be greater than 0.");
                                    }
                                } else {
                                    System.out.println("Invalid input. Please enter a valid integer.");
                                    in.nextLine();
                                }
                            }

                            boolean allocated = false;

                            // ===== FIRST FIT =====
                            if (allocStrat == 1) {
                                for (a = 0; a < PartNum; a++) {
                                    if (Pname[a] == null && available[a] >= newSize[p]) {
                                        Pname[a] = newName[p];
                                        JobSize[a] = newSize[p];
                                        available[a] -= newSize[p];
                                        allocated = true;
                                        break;
                                    }
                                }
                                if (!allocated)
                                    System.out.println("Process " + newName[p] + " could not be allocated (First Fit).");
                                else
                                    System.out.println("Process " + newName[p] + " allocated (First Fit).");
                            }

                            // ===== BEST FIT =====
                            else if (allocStrat == 2) {
                                int bestIdx = -1;
                                int minSpaceLeft = Integer.MAX_VALUE;
                                for (a = 0; a < PartNum; a++) {
                                    if (Pname[a] == null && available[a] >= newSize[p]) {
                                        int spaceLeft = available[a] - newSize[p];
                                        if (spaceLeft < minSpaceLeft) {
                                            minSpaceLeft = spaceLeft;
                                            bestIdx = a;
                                        }
                                    }
                                }
                                if (bestIdx != -1) {
                                    Pname[bestIdx] = newName[p];
                                    JobSize[bestIdx] = newSize[p];
                                    available[bestIdx] -= newSize[p];
                                    allocated = true;
                                } else {
                                    System.out.println("Process " + newName[p] + " could not be allocated (Best Fit).");
                                }
                            }

                            // Display current memory status
                            System.out.println("----------------------------------------------------------------");
                            System.out.printf("| %-10s | %-18s | %-15s | %-10s \n",
                                    "Partition", "Memory Block Size", "Process Name", "Job Size");
                            System.out.println("----------------------------------------------------------------");
                            for (int i = 0; i < PartNum; i++) {
                                if (Pname[i] != null && JobSize[i] > 0) {
                                    System.out.printf("| %-10d | %6dK%-10s | %-15s | %6dK     \n",
                                            (i + 1), partition[i], "", Pname[i], JobSize[i]);
                                } else {
                                    System.out.printf("| %-10d | %6dK%-10s | %-15s | %-10s \n",
                                            (i + 1), partition[i], "", "-", "-");
                                }
                            }
                            System.out.println("----------------------------------------------------------------");
                        }
                        break;

                    case 4: // Deallocate
                        if (PartNum == 0) {
                            System.out.println("Please set memory partitions first using option [2].");
                            break;
                        }

                        System.out.print("Enter process name to deallocate: ");
                        String DeallocName = in.nextLine().trim();

                        if (DeallocName.isEmpty()) {
                            System.out.println("Process name cannot be empty.");
                            break;
                        }

                        boolean found = false;
                        for (a = 0; a < PartNum; a++) {
                            if (DeallocName.equalsIgnoreCase(Pname[a])) {
                                available[a] += JobSize[a];
                                System.out.println("Process " + DeallocName + " deallocated successfully");
                                Pname[a] = null;
                                JobSize[a] = 0;
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("Process not found.");
                     } else{
                            System.out.println("---------------------------------------------------------------------");
                            System.out.printf("| %-10s | %-18s | %-15s | %-10s \n",
                                    "Partition", "Memory Block Size", "Process Name", "Job Size");
                            System.out.println("---------------------------------------------------------------------");
                            int totalPartition = 0, totalUsed = 0;
                            for (int i = 0; i < PartNum; i++) {
                                totalPartition += partition[i];
                                if (Pname[i] != null && JobSize[i] > 0) {
                                    totalUsed += JobSize[i];
                                    System.out.printf("| %-10d | %6dK%-10s | %-15s | %6dK     \n",
                                            (i + 1), partition[i], "", Pname[i], JobSize[i]);
                                } else {
                                    System.out.printf("| %-10d | %6dK%-10s | %-15s | %-10s \n",
                                            (i + 1), partition[i], "", "-", "-");
                                }
                            }
                            System.out.println("---------------------------------------------------------------------");
                            System.out.printf("| %-25s %6dK | %-20s %6dK |\n", "Total Partitions Size:", totalPartition, "Total Used:", totalUsed);
                            System.out.println("---------------------------------------------------------------------");
                        }
                
                        break;

                    case 5: // View memory status
                        if (PartNum == 0) {
                            System.out.println("Please set memory partitions first using option [2].");
                            break;
                        }
                        System.out.println("------------------------------------------------------------------------");
                        System.out.printf("| %-9s | %-19s | %-13s | %-9s | %-6s |\n",
                                "Partition", "Memory Block Size", "Process Name", "Job Size", "Status");
                        System.out.println("------------------------------------------------------------------------");

                        int totalAvailable = 0, totalUsed = 0;
                        for (a = 0; a < PartNum; a++) {
                            totalAvailable += partition[a];
                            if (Pname[a] != null && JobSize[a] > 0) {
                                totalUsed += JobSize[a];
                                System.out.printf(
                                        "| %-9d | %-19s | %-13s | %-9s | %-6s |\n",
                                        (a + 1),
                                        partition[a] + "K",
                                        Pname[a],
                                        JobSize[a] + "K",
                                        "Busy");
                            } else {
                                System.out.printf(
                                        "| %-9d | %-19s | %-13s | %-9s | %-6s |\n",
                                        (a + 1),
                                        partition[a] + "K",
                                        "-",
                                        "-",
                                        "Free");
                            }
                        }
                        System.out.println("------------------------------------------------------------------------");
                        System.out.printf(" %-17s %7s | %-14s %8s \n",
                                "Total Available:", totalAvailable + "K", "Total Used:", totalUsed + "K");
                        System.out.println("------------------------------------------------------------------------");
                        break;

                    case 6:
                        System.out.println("Goodbye!");
                        break;
                }

            } while (choice != 6);

        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            in.close();
        }
    }
}