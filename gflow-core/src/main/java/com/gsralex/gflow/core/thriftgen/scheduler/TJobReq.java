/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.gsralex.gflow.core.thriftgen.scheduler;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2019-02-14")
public class TJobReq implements org.apache.thrift.TBase<TJobReq, TJobReq._Fields>, java.io.Serializable, Cloneable, Comparable<TJobReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TJobReq");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField ACTION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("actionId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField JOB_GROUP_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("jobGroupId", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField CLASS_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("className", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PARAMETER_FIELD_DESC = new org.apache.thrift.protocol.TField("parameter", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField INDEX_FIELD_DESC = new org.apache.thrift.protocol.TField("index", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField RETRY_JOB_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("retryJobId", org.apache.thrift.protocol.TType.I64, (short)7);
  private static final org.apache.thrift.protocol.TField ACCESS_TOKEN_FIELD_DESC = new org.apache.thrift.protocol.TField("accessToken", org.apache.thrift.protocol.TType.STRING, (short)8);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TJobReqStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TJobReqTupleSchemeFactory();

  public long id; // required
  public long actionId; // required
  public long jobGroupId; // required
  public java.lang.String className; // required
  public java.lang.String parameter; // required
  public int index; // required
  public long retryJobId; // required
  public java.lang.String accessToken; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    ACTION_ID((short)2, "actionId"),
    JOB_GROUP_ID((short)3, "jobGroupId"),
    CLASS_NAME((short)4, "className"),
    PARAMETER((short)5, "parameter"),
    INDEX((short)6, "index"),
    RETRY_JOB_ID((short)7, "retryJobId"),
    ACCESS_TOKEN((short)8, "accessToken");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ID
          return ID;
        case 2: // ACTION_ID
          return ACTION_ID;
        case 3: // JOB_GROUP_ID
          return JOB_GROUP_ID;
        case 4: // CLASS_NAME
          return CLASS_NAME;
        case 5: // PARAMETER
          return PARAMETER;
        case 6: // INDEX
          return INDEX;
        case 7: // RETRY_JOB_ID
          return RETRY_JOB_ID;
        case 8: // ACCESS_TOKEN
          return ACCESS_TOKEN;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ID_ISSET_ID = 0;
  private static final int __ACTIONID_ISSET_ID = 1;
  private static final int __JOBGROUPID_ISSET_ID = 2;
  private static final int __INDEX_ISSET_ID = 3;
  private static final int __RETRYJOBID_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ACTION_ID, new org.apache.thrift.meta_data.FieldMetaData("actionId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.JOB_GROUP_ID, new org.apache.thrift.meta_data.FieldMetaData("jobGroupId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CLASS_NAME, new org.apache.thrift.meta_data.FieldMetaData("className", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARAMETER, new org.apache.thrift.meta_data.FieldMetaData("parameter", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.INDEX, new org.apache.thrift.meta_data.FieldMetaData("index", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.RETRY_JOB_ID, new org.apache.thrift.meta_data.FieldMetaData("retryJobId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ACCESS_TOKEN, new org.apache.thrift.meta_data.FieldMetaData("accessToken", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TJobReq.class, metaDataMap);
  }

  public TJobReq() {
  }

  public TJobReq(
    long id,
    long actionId,
    long jobGroupId,
    java.lang.String className,
    java.lang.String parameter,
    int index,
    long retryJobId,
    java.lang.String accessToken)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.actionId = actionId;
    setActionIdIsSet(true);
    this.jobGroupId = jobGroupId;
    setJobGroupIdIsSet(true);
    this.className = className;
    this.parameter = parameter;
    this.index = index;
    setIndexIsSet(true);
    this.retryJobId = retryJobId;
    setRetryJobIdIsSet(true);
    this.accessToken = accessToken;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TJobReq(TJobReq other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.actionId = other.actionId;
    this.jobGroupId = other.jobGroupId;
    if (other.isSetClassName()) {
      this.className = other.className;
    }
    if (other.isSetParameter()) {
      this.parameter = other.parameter;
    }
    this.index = other.index;
    this.retryJobId = other.retryJobId;
    if (other.isSetAccessToken()) {
      this.accessToken = other.accessToken;
    }
  }

  public TJobReq deepCopy() {
    return new TJobReq(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setActionIdIsSet(false);
    this.actionId = 0;
    setJobGroupIdIsSet(false);
    this.jobGroupId = 0;
    this.className = null;
    this.parameter = null;
    setIndexIsSet(false);
    this.index = 0;
    setRetryJobIdIsSet(false);
    this.retryJobId = 0;
    this.accessToken = null;
  }

  public long getId() {
    return this.id;
  }

  public TJobReq setId(long id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public long getActionId() {
    return this.actionId;
  }

  public TJobReq setActionId(long actionId) {
    this.actionId = actionId;
    setActionIdIsSet(true);
    return this;
  }

  public void unsetActionId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ACTIONID_ISSET_ID);
  }

  /** Returns true if field actionId is set (has been assigned a value) and false otherwise */
  public boolean isSetActionId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ACTIONID_ISSET_ID);
  }

  public void setActionIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ACTIONID_ISSET_ID, value);
  }

  public long getJobGroupId() {
    return this.jobGroupId;
  }

  public TJobReq setJobGroupId(long jobGroupId) {
    this.jobGroupId = jobGroupId;
    setJobGroupIdIsSet(true);
    return this;
  }

  public void unsetJobGroupId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __JOBGROUPID_ISSET_ID);
  }

  /** Returns true if field jobGroupId is set (has been assigned a value) and false otherwise */
  public boolean isSetJobGroupId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __JOBGROUPID_ISSET_ID);
  }

  public void setJobGroupIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __JOBGROUPID_ISSET_ID, value);
  }

  public java.lang.String getClassName() {
    return this.className;
  }

  public TJobReq setClassName(java.lang.String className) {
    this.className = className;
    return this;
  }

  public void unsetClassName() {
    this.className = null;
  }

  /** Returns true if field className is set (has been assigned a value) and false otherwise */
  public boolean isSetClassName() {
    return this.className != null;
  }

  public void setClassNameIsSet(boolean value) {
    if (!value) {
      this.className = null;
    }
  }

  public java.lang.String getParameter() {
    return this.parameter;
  }

  public TJobReq setParameter(java.lang.String parameter) {
    this.parameter = parameter;
    return this;
  }

  public void unsetParameter() {
    this.parameter = null;
  }

  /** Returns true if field parameter is set (has been assigned a value) and false otherwise */
  public boolean isSetParameter() {
    return this.parameter != null;
  }

  public void setParameterIsSet(boolean value) {
    if (!value) {
      this.parameter = null;
    }
  }

  public int getIndex() {
    return this.index;
  }

  public TJobReq setIndex(int index) {
    this.index = index;
    setIndexIsSet(true);
    return this;
  }

  public void unsetIndex() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __INDEX_ISSET_ID);
  }

  /** Returns true if field index is set (has been assigned a value) and false otherwise */
  public boolean isSetIndex() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __INDEX_ISSET_ID);
  }

  public void setIndexIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __INDEX_ISSET_ID, value);
  }

  public long getRetryJobId() {
    return this.retryJobId;
  }

  public TJobReq setRetryJobId(long retryJobId) {
    this.retryJobId = retryJobId;
    setRetryJobIdIsSet(true);
    return this;
  }

  public void unsetRetryJobId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __RETRYJOBID_ISSET_ID);
  }

  /** Returns true if field retryJobId is set (has been assigned a value) and false otherwise */
  public boolean isSetRetryJobId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __RETRYJOBID_ISSET_ID);
  }

  public void setRetryJobIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __RETRYJOBID_ISSET_ID, value);
  }

  public java.lang.String getAccessToken() {
    return this.accessToken;
  }

  public TJobReq setAccessToken(java.lang.String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  public void unsetAccessToken() {
    this.accessToken = null;
  }

  /** Returns true if field accessToken is set (has been assigned a value) and false otherwise */
  public boolean isSetAccessToken() {
    return this.accessToken != null;
  }

  public void setAccessTokenIsSet(boolean value) {
    if (!value) {
      this.accessToken = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((java.lang.Long)value);
      }
      break;

    case ACTION_ID:
      if (value == null) {
        unsetActionId();
      } else {
        setActionId((java.lang.Long)value);
      }
      break;

    case JOB_GROUP_ID:
      if (value == null) {
        unsetJobGroupId();
      } else {
        setJobGroupId((java.lang.Long)value);
      }
      break;

    case CLASS_NAME:
      if (value == null) {
        unsetClassName();
      } else {
        setClassName((java.lang.String)value);
      }
      break;

    case PARAMETER:
      if (value == null) {
        unsetParameter();
      } else {
        setParameter((java.lang.String)value);
      }
      break;

    case INDEX:
      if (value == null) {
        unsetIndex();
      } else {
        setIndex((java.lang.Integer)value);
      }
      break;

    case RETRY_JOB_ID:
      if (value == null) {
        unsetRetryJobId();
      } else {
        setRetryJobId((java.lang.Long)value);
      }
      break;

    case ACCESS_TOKEN:
      if (value == null) {
        unsetAccessToken();
      } else {
        setAccessToken((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case ACTION_ID:
      return getActionId();

    case JOB_GROUP_ID:
      return getJobGroupId();

    case CLASS_NAME:
      return getClassName();

    case PARAMETER:
      return getParameter();

    case INDEX:
      return getIndex();

    case RETRY_JOB_ID:
      return getRetryJobId();

    case ACCESS_TOKEN:
      return getAccessToken();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case ACTION_ID:
      return isSetActionId();
    case JOB_GROUP_ID:
      return isSetJobGroupId();
    case CLASS_NAME:
      return isSetClassName();
    case PARAMETER:
      return isSetParameter();
    case INDEX:
      return isSetIndex();
    case RETRY_JOB_ID:
      return isSetRetryJobId();
    case ACCESS_TOKEN:
      return isSetAccessToken();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TJobReq)
      return this.equals((TJobReq)that);
    return false;
  }

  public boolean equals(TJobReq that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_actionId = true;
    boolean that_present_actionId = true;
    if (this_present_actionId || that_present_actionId) {
      if (!(this_present_actionId && that_present_actionId))
        return false;
      if (this.actionId != that.actionId)
        return false;
    }

    boolean this_present_jobGroupId = true;
    boolean that_present_jobGroupId = true;
    if (this_present_jobGroupId || that_present_jobGroupId) {
      if (!(this_present_jobGroupId && that_present_jobGroupId))
        return false;
      if (this.jobGroupId != that.jobGroupId)
        return false;
    }

    boolean this_present_className = true && this.isSetClassName();
    boolean that_present_className = true && that.isSetClassName();
    if (this_present_className || that_present_className) {
      if (!(this_present_className && that_present_className))
        return false;
      if (!this.className.equals(that.className))
        return false;
    }

    boolean this_present_parameter = true && this.isSetParameter();
    boolean that_present_parameter = true && that.isSetParameter();
    if (this_present_parameter || that_present_parameter) {
      if (!(this_present_parameter && that_present_parameter))
        return false;
      if (!this.parameter.equals(that.parameter))
        return false;
    }

    boolean this_present_index = true;
    boolean that_present_index = true;
    if (this_present_index || that_present_index) {
      if (!(this_present_index && that_present_index))
        return false;
      if (this.index != that.index)
        return false;
    }

    boolean this_present_retryJobId = true;
    boolean that_present_retryJobId = true;
    if (this_present_retryJobId || that_present_retryJobId) {
      if (!(this_present_retryJobId && that_present_retryJobId))
        return false;
      if (this.retryJobId != that.retryJobId)
        return false;
    }

    boolean this_present_accessToken = true && this.isSetAccessToken();
    boolean that_present_accessToken = true && that.isSetAccessToken();
    if (this_present_accessToken || that_present_accessToken) {
      if (!(this_present_accessToken && that_present_accessToken))
        return false;
      if (!this.accessToken.equals(that.accessToken))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(id);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(actionId);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(jobGroupId);

    hashCode = hashCode * 8191 + ((isSetClassName()) ? 131071 : 524287);
    if (isSetClassName())
      hashCode = hashCode * 8191 + className.hashCode();

    hashCode = hashCode * 8191 + ((isSetParameter()) ? 131071 : 524287);
    if (isSetParameter())
      hashCode = hashCode * 8191 + parameter.hashCode();

    hashCode = hashCode * 8191 + index;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(retryJobId);

    hashCode = hashCode * 8191 + ((isSetAccessToken()) ? 131071 : 524287);
    if (isSetAccessToken())
      hashCode = hashCode * 8191 + accessToken.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TJobReq other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetActionId()).compareTo(other.isSetActionId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActionId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.actionId, other.actionId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetJobGroupId()).compareTo(other.isSetJobGroupId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetJobGroupId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.jobGroupId, other.jobGroupId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetClassName()).compareTo(other.isSetClassName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClassName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.className, other.className);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetParameter()).compareTo(other.isSetParameter());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParameter()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.parameter, other.parameter);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIndex()).compareTo(other.isSetIndex());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIndex()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.index, other.index);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetRetryJobId()).compareTo(other.isSetRetryJobId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRetryJobId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.retryJobId, other.retryJobId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetAccessToken()).compareTo(other.isSetAccessToken());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAccessToken()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.accessToken, other.accessToken);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TJobReq(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("actionId:");
    sb.append(this.actionId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("jobGroupId:");
    sb.append(this.jobGroupId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("className:");
    if (this.className == null) {
      sb.append("null");
    } else {
      sb.append(this.className);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("parameter:");
    if (this.parameter == null) {
      sb.append("null");
    } else {
      sb.append(this.parameter);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("index:");
    sb.append(this.index);
    first = false;
    if (!first) sb.append(", ");
    sb.append("retryJobId:");
    sb.append(this.retryJobId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("accessToken:");
    if (this.accessToken == null) {
      sb.append("null");
    } else {
      sb.append(this.accessToken);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TJobReqStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TJobReqStandardScheme getScheme() {
      return new TJobReqStandardScheme();
    }
  }

  private static class TJobReqStandardScheme extends org.apache.thrift.scheme.StandardScheme<TJobReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TJobReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.id = iprot.readI64();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ACTION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.actionId = iprot.readI64();
              struct.setActionIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // JOB_GROUP_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.jobGroupId = iprot.readI64();
              struct.setJobGroupIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CLASS_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.className = iprot.readString();
              struct.setClassNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PARAMETER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.parameter = iprot.readString();
              struct.setParameterIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // INDEX
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.index = iprot.readI32();
              struct.setIndexIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // RETRY_JOB_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.retryJobId = iprot.readI64();
              struct.setRetryJobIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // ACCESS_TOKEN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.accessToken = iprot.readString();
              struct.setAccessTokenIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TJobReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI64(struct.id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ACTION_ID_FIELD_DESC);
      oprot.writeI64(struct.actionId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(JOB_GROUP_ID_FIELD_DESC);
      oprot.writeI64(struct.jobGroupId);
      oprot.writeFieldEnd();
      if (struct.className != null) {
        oprot.writeFieldBegin(CLASS_NAME_FIELD_DESC);
        oprot.writeString(struct.className);
        oprot.writeFieldEnd();
      }
      if (struct.parameter != null) {
        oprot.writeFieldBegin(PARAMETER_FIELD_DESC);
        oprot.writeString(struct.parameter);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(INDEX_FIELD_DESC);
      oprot.writeI32(struct.index);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(RETRY_JOB_ID_FIELD_DESC);
      oprot.writeI64(struct.retryJobId);
      oprot.writeFieldEnd();
      if (struct.accessToken != null) {
        oprot.writeFieldBegin(ACCESS_TOKEN_FIELD_DESC);
        oprot.writeString(struct.accessToken);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TJobReqTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TJobReqTupleScheme getScheme() {
      return new TJobReqTupleScheme();
    }
  }

  private static class TJobReqTupleScheme extends org.apache.thrift.scheme.TupleScheme<TJobReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TJobReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetActionId()) {
        optionals.set(1);
      }
      if (struct.isSetJobGroupId()) {
        optionals.set(2);
      }
      if (struct.isSetClassName()) {
        optionals.set(3);
      }
      if (struct.isSetParameter()) {
        optionals.set(4);
      }
      if (struct.isSetIndex()) {
        optionals.set(5);
      }
      if (struct.isSetRetryJobId()) {
        optionals.set(6);
      }
      if (struct.isSetAccessToken()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetId()) {
        oprot.writeI64(struct.id);
      }
      if (struct.isSetActionId()) {
        oprot.writeI64(struct.actionId);
      }
      if (struct.isSetJobGroupId()) {
        oprot.writeI64(struct.jobGroupId);
      }
      if (struct.isSetClassName()) {
        oprot.writeString(struct.className);
      }
      if (struct.isSetParameter()) {
        oprot.writeString(struct.parameter);
      }
      if (struct.isSetIndex()) {
        oprot.writeI32(struct.index);
      }
      if (struct.isSetRetryJobId()) {
        oprot.writeI64(struct.retryJobId);
      }
      if (struct.isSetAccessToken()) {
        oprot.writeString(struct.accessToken);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TJobReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.id = iprot.readI64();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.actionId = iprot.readI64();
        struct.setActionIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.jobGroupId = iprot.readI64();
        struct.setJobGroupIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.className = iprot.readString();
        struct.setClassNameIsSet(true);
      }
      if (incoming.get(4)) {
        struct.parameter = iprot.readString();
        struct.setParameterIsSet(true);
      }
      if (incoming.get(5)) {
        struct.index = iprot.readI32();
        struct.setIndexIsSet(true);
      }
      if (incoming.get(6)) {
        struct.retryJobId = iprot.readI64();
        struct.setRetryJobIdIsSet(true);
      }
      if (incoming.get(7)) {
        struct.accessToken = iprot.readString();
        struct.setAccessTokenIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

