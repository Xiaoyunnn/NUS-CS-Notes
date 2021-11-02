import java.util.ArrayList;
import java.util.Arrays;
public class Solution {
    // TODO: Include your data structures here
    int numTeams;
    ArrayList<Integer> numSolvedList;
    ArrayList<Long> numPenaltyList;
    Team root = null;

    public class Team implements Comparable<Team>{
//        long[] dataSet;
        // [teamId, numSolved, numPenalty]
        int height;
        int weight;
        int teamId;
        int numSolved;
        long numPenalty;
        Team left = null;
        Team right = null;
        Team parent = null;

//        public Team(long[] dataSet) {
//            this.dataSet = dataSet;
//            this.height = 0;
//            this.weight = 1;
//        }

        public Team(int teamId, int numSolved, long numPenalty) {
            this.teamId = teamId;
            this.numSolved = numSolved;
            this.numPenalty = numPenalty;
            this.height = 0;
            this.weight = 1;
        }

        @Override
        public int compareTo(Team o) {
            int compareSolved = Integer.compare(this.numSolved, o.numSolved);
            int comparePenalty = Long.compare(this.numPenalty, o.numPenalty);
            int compareId = Integer.compare(this.teamId, o.teamId);
            return compareSolved == 0
                    ? comparePenalty == 0
                        ? compareId
                        : comparePenalty
                    : compareSolved;
        }
    }
    int weight(Team team) {
        if (team == null) {
            return 0;
        } else {
            return team.weight;
        }
    }
    int height(Team team) {
        if (team == null) {
            return 0;
        } else {
            return team.height + 1;
        }
    }
    public Solution(int numTeams) {
        // TODO: Construct/Initialise your data structures here
        this.numTeams = numTeams;
        this.numSolvedList = new ArrayList<>();
        this.numPenaltyList = new ArrayList<>();
        this.numSolvedList.add(0);
        this.numPenaltyList.add(0L);
        for (int i = 1; i <= numTeams; i++) {
            insert(new Team(i, 0, 0));
            this.numSolvedList.add(0);
            this.numPenaltyList.add(0L);
        }
    }
//    int compare(long[] dataSet1, long[] dataSet2) {
//        if (dataSet1[1] != dataSet2[1]) {
//            return (int) (-dataSet1[1] + dataSet2[1]);
//        } else if (dataSet1[2] != dataSet2[2]) {
//            return (int) (dataSet1[2] - dataSet2[2]);
//        } else {
//            return (int) (dataSet1[0] - dataSet2[0]);
//        }
//    }
    void insert(Team other) {
        root = insert(root, other);
//        if (this.search(dataSet) == null) {
//            if (this.root == null) {
//                this.root = new Team(dataSet);
//            } else if (compare(dataSet, root.dataSet) < 0) {
//                this.root.left = this.insert(this.root.left, dataSet);
//            } else if (compare(dataSet, root.dataSet) > 0) {
//                this.root.right = this.insert(this.root.right, dataSet);
//            }
        this.root = this.balance(this.root);
//        }
    }
    Team insert(Team node, Team other) {
        if (node == null) {
            return other;
        } else {
            if (other.compareTo(node) < 0) {
                node.left = insert(node.left, other);
            } else {
                node.right = insert(node.right, other);
            }
            node = this.balance(node);
            return node;
        }
    }
    Team rotateRight(Team node) {
        Team newRoot = node.left;
        Team temp = newRoot.right;
        newRoot.right = node;
        node.left = temp;
        update(newRoot.left);
        update(newRoot.right);
        update(newRoot);
        return newRoot;
    }
    void update(Team node) {
        if (node != null) {
            node.height = (Math.max(height(node.left), height(node.right)));
            node.weight = 1 + weight(node.left) + weight(node.right);
        }
    }
    Team rotateLeft(Team node) {
        Team newRoot = node.right;
        Team temp = newRoot.left;
        newRoot.left = node;
        node.right = temp;
        update(newRoot.left);
        update(newRoot.right);
        update(newRoot);
        return newRoot;
    }
    int balanceFactor (Team node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }
    //    Team minTeam(Team node) {
//        Team curr = node;
//        if (curr == null) {
//            return null;
//        } else {
//            while (curr.left != null) {
//                curr = curr.left;
//            }
//            return curr;
//        }
//    }
    Team maxTeam(Team node) {
        Team curr = node;
        if (curr == null) {
            return null;
        } else {
            while (curr.right != null) {
                curr = curr.right;
            }
            return curr;
        }
    }
    public void delete(Team data) {
        root = delete(root, data);
//        Team curr = search(data);
//        if (curr != null) {
//            this.root = delete(root, data);
//        }
    }
    public Team delete(Team curr, Team data) {
        if (curr == null) {
            return null;
        } else {
            if (data.compareTo(curr) < 0) {
                curr.left = delete(curr.left, data);
            } else if (data.compareTo(curr) > 0) {
                curr.right = delete(curr.right, data);
            } else {
                // this is the node to delete
                int numChildren = 0;
                if (curr.left != null) {
                    numChildren++;
                }
                if (curr.right != null) {
                    numChildren++;
                }
                if (numChildren == 0) {
                    curr = null;
                    return null;
                }
                if (numChildren == 1) {
                    if (curr.left == null) {
                        return curr.right;
                    } else {
                        return curr.left;
                    }
                }
                Team max = maxTeam(curr.left);
                curr.teamId = max.teamId;
                curr.numPenalty = max.numPenalty;
                curr.numSolved = max.numSolved;
//                curr.dataSet = max.dataSet;

                curr.left = delete(curr.left, new Team(max.teamId, max.numSolved, max.numPenalty));
            }
            curr = balance(curr);
            return curr;
        }
    }
    public Team balance(Team curr) {
        if (balanceFactor(curr) > 1) {
            if (balanceFactor(curr.left) > 0) {
                // left left heavy
                curr = rotateRight(curr);
            } else {
                // left right heavy
                curr.left = rotateLeft(curr.left);
                curr = rotateRight(curr);
            }
        } else if (balanceFactor(curr) < -1) {
            if (balanceFactor(curr.right) < 0) {
                // right right heavy
                curr = rotateLeft(curr);
            } else {
                // right left heavy
                curr.right = rotateRight(curr.right);
                curr = rotateLeft(curr);
            }
        }
        update(curr.left);
        update(curr.right);
        update(curr);
        return curr;
    }
    Team search(Team dataSet) {
        Team curr = root;
        while (curr != null) {
            if (curr.compareTo(dataSet) == 0) {
                break;
            }
            if (dataSet.compareTo(curr) < 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return curr;
    }
    int rank(Team node) {
        return rank(node, root);
    }
    int rank(Team node, Team curr) {
        if (curr == null) {
            return 0;
        } else if (node.compareTo(curr) < 0) {
            // left child
            return rank(node, curr.left);
        } else if (node.compareTo(curr) > 0) {
            return weight(curr.left) + 1 + rank(node, curr.right);
        } else {
            return weight(curr.left);
        }
    }
    public int update(int team, long newPenalty){
        // TODO: Implement your update function here
        int solved = this.numSolvedList.get(team);
        long penalty = this.numPenaltyList.get(team);
//        long[] dataSet = new long[]{(long) team, solved, penalty};
        delete(new Team(team, solved, penalty));
//        long[] newData = new long[] {(long) team, solved + 1, penalty + newPenalty};
        insert(new Team(team, solved+1, penalty + newPenalty));
        this.numSolvedList.set(team, solved + 1);
        this.numPenaltyList.set(team, newPenalty + penalty);
//        long[] teamOneData = new long[]{1, this.numSolvedList.get(1), this.numPenaltyList.get(1)};
        Team team1 = new Team(1, this.numSolvedList.get(1), this.numPenaltyList.get(1));
        // if inserted, team1 will always be inserted after the last one
        // rank(team1) gives the no. of teams before the updated one
        // return this.numTeams - rank(team1) + 1;
        return rank(team1) + 1;
    }
}