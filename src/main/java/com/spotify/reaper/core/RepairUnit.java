/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.spotify.reaper.core;

import java.util.Set;

public class RepairUnit {

  private final long id;
  private final String clusterName;
  private final String keyspaceName;
  private final Set<String> columnFamilies;
  private final int segmentCount;
  private final RepairParallelism repairParallelism;

  private RepairUnit(Builder builder, long id) {
    this.id = id;
    this.clusterName = builder.clusterName;
    this.keyspaceName = builder.keyspaceName;
    this.columnFamilies = builder.columnFamilies;
    this.segmentCount = builder.segmentCount;
    this.repairParallelism = builder.repairParallelism;
  }

  public long getId() {
    return id;
  }

  public String getClusterName() {
    return clusterName;
  }

  public String getKeyspaceName() {
    return keyspaceName;
  }

  public Set<String> getColumnFamilies() {
    return columnFamilies;
  }

  public int getSegmentCount() {
    return segmentCount;
  }

  public RepairParallelism getRepairParallelism() {
    return repairParallelism;
  }

  public Builder with() {
    return new Builder(this);
  }

  public enum RepairParallelism {
    PARALLEL,   // run repairs in parallel on all nodes for a token range
    SEQUENTIAL, // run repairs in one node at the time for a token range
    DC_PARALLEL // run repairs one node at a time per data center
  }

  public static class Builder {

    public final String clusterName;
    public final String keyspaceName;
    public final Set<String> columnFamilies;
    private int segmentCount;
    private RepairParallelism repairParallelism;

    public Builder(String clusterName, String keyspaceName, Set<String> columnFamilies,
                   int segmentCount, RepairParallelism repairParallelism) {
      this.clusterName = clusterName;
      this.keyspaceName = keyspaceName;
      this.columnFamilies = columnFamilies;
      this.segmentCount = segmentCount;
      this.repairParallelism = repairParallelism;
    }

    private Builder(RepairUnit original) {
      clusterName = original.clusterName;
      keyspaceName = original.keyspaceName;
      columnFamilies = original.columnFamilies;
      segmentCount = original.segmentCount;
      repairParallelism = original.repairParallelism;
    }

    public Builder segmentCount(int segmentCount) {
      this.segmentCount = segmentCount;
      return this;
    }

    public Builder repairParallelism(RepairParallelism repairParallelism) {
      this.repairParallelism = repairParallelism;
      return this;
    }

    public RepairUnit build(long id) {
      return new RepairUnit(this, id);
    }
  }
}
