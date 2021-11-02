public class Solution {
    // TODO: Include your data structures here
    Quest root = null;

    class Quest implements Comparable<Quest> {
        public long energy;
        public long reward;
        int height;
        int weight;
        Quest left = null;
        Quest right = null;
        Quest parent = null;

        public Quest(long energy, long reward) {
            this.energy = energy;
            this.reward = reward;
            this.height = 0;
            this.weight = 1;
        }

        public int compareTo(Quest quest) {
            int compareEnergy = Long.compare(this.energy, quest.energy);
            return compareEnergy == 0
                    ? Long.compare(this.reward, quest.reward)
                    : compareEnergy;
        }
    }

        int weight(Quest team) {
            if (team == null) {
                return 0;
            } else {
                return team.weight;
            }
        }

        int height(Quest team) {
            if (team == null) {
                return 0;
            } else {
                return team.height + 1;
            }
        }

        void insert(long energy, long value) {
            root = insert(root, energy, value);
            this.root = this.balance(this.root);
        }

        Quest insert(Quest node, long energy, long value) {
            if (node == null) {
                return new Quest(energy, value);
            } else {
                if ((new Quest(energy, value)).compareTo(node) < 0) {
                    node.left = insert(node.left, energy, value);
                } else {
                    node.right = insert(node.right, energy, value);
                }
                node = this.balance(node);
                return node;
            }
        }

        Quest rotateRight(Quest node) {
            Quest newRoot = node.left;
            Quest temp = newRoot.right;

            newRoot.right = node;
            node.left = temp;

            update(newRoot.left);
            update(newRoot.right);
            update(newRoot);

            return newRoot;
        }

        void update(Quest node) {
            if (node != null) {
                node.height = (Math.max(height(node.left), height(node.right)));
                node.weight = 1 + weight(node.left) + weight(node.right);
            }
        }

        Quest rotateLeft(Quest node) {
            Quest newRoot = node.right;
            Quest temp = newRoot.left;

            newRoot.left = node;
            node.right = temp;

            update(newRoot.left);
            update(newRoot.right);
            update(newRoot);
            return newRoot;
        }



        int balanceFactor (Quest node) {
            if (node == null) {
                return 0;
            }
            return height(node.left) - height(node.right);
        }

    Quest minEnergyQuest(Quest node) {
        Quest curr = node;
        if (curr == null) {
            return null;
        } else {
            while (curr.left != null) {
                curr = curr.left;
            }
            return curr;
        }
    }

        Quest maxEnergyQuest(Quest node) {
            Quest curr = node;
            if (curr == null) {
                return null;
            } else {
                while (curr.right != null) {
                    curr = curr.right;
                }
                return curr;
            }
        }

        public void delete(long energy, long value) {
            root = delete(root, energy, value);
        }

        public Quest delete(Quest curr, long energy, long value) {
            if (curr == null) {
                return null;
            } else {
                if ((new Quest(energy, value)).compareTo(curr) < 0) {
                    curr.left = delete(curr.left, energy, value);
                } else if ((new Quest(energy, value)).compareTo(curr) > 0) {
                    curr.right = delete(curr.right, energy, value);
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

                    Quest max = maxEnergyQuest(curr.left);
                    curr.energy = max.energy;
                    curr.reward = max.reward;

                    curr.left = delete(curr.left, max.energy, max.reward);
                }
                curr = balance(curr);
                return curr;
            }
        }

        public Quest balance(Quest curr) {
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

        Quest search(long energy, long value) {
            Quest curr = root;
            while (curr != null) {
                if (curr.energy == energy && curr.reward == value) {
                    break;
                }

                if ((new Quest(energy, value)).compareTo(curr) < 0) {
                    curr = curr.left;
                } else {
                    curr = curr.right;
                }
            }
            return curr;
        }

        int rank(Quest node) {
            return rank(node, root);
        }

        int rank(Quest node, Quest curr) {
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

        Quest findPredecessor(Quest quest) {
            return findPredecessor(root, quest);
        }

        Quest findPredecessor(Quest curr, Quest quest) {
            if (curr == null) {
                return null;
            }

            Quest res = null;

            if (quest.compareTo(curr) == 0) {
                // curr node is equal to quest
                if (curr.left != null) {
                    Quest temp = curr.left;
                    while (temp.right != null) {
                        temp = temp.right;
                    }
                    res = temp;
                }
            } else if (quest.compareTo(curr) > 0) {
                res = curr;
                findPredecessor(curr.right, quest);
            } else {
                findPredecessor(curr.left, quest);
            }
            return res;
        }

    public Solution() {
        // TODO: Construct/Initialise your data structures here
    }

    void add(long energy, long value) {
        // TODO: Implement your insertion operation here
        insert(energy, value);
    }

    long query(long remainingEnergy) {
        // TODO: Implement your query operation here

        Quest maxQuestAvail = findPredecessor(new Quest((remainingEnergy + 1L), 0L));

        if (maxQuestAvail == null) {
            return 0;
        } else {
            return query(maxQuestAvail, remainingEnergy, 0);
        }



        //long totalReward = 0L;
        /*
        for(Quest maxNode = findPredecessor(new Quest(remainingEnergy + 1L, 0L));
            maxNode != null && remainingEnergy >= maxNode.energy;
            maxNode = findPredecessor(new Quest(remainingEnergy + 1L, 0L))) {

            totalReward += maxNode.reward;
            remainingEnergy -= maxNode.energy;
            delete(maxNode.energy, maxNode.reward);
        }*/


        //return totalReward;

    }

    long query(Quest max, long remainingEnergy, long totalReward) {
        if (max == null || remainingEnergy < max.energy) {
            return totalReward;
        } else {
            long energyRemained = remainingEnergy - max.energy;
            long rewardAdded = totalReward + max.reward;
            delete(max.energy, max.reward);
            Quest maxQuestAvail = findPredecessor(new Quest((remainingEnergy + 1L), 0L));
            return query(maxQuestAvail, energyRemained, rewardAdded);


        }

    }

    public static void main(String[] args) {
        Solution soln = new Solution();
        long ret = 0L;
        soln.add(8L, 10L);
        soln.add(3L, 25L);
        soln.add(5L, 6L);
        ret = soln.query(7L);
        System.out.println(ret);
        ret = soln.query(7L);
        System.out.println(ret);
        soln.add(1L, 9L);
        soln.add(2L, 13L);
        ret = soln.query(20L);
        System.out.println(ret);
        ret = soln.query(1L);
        System.out.println(ret);
    }

}
