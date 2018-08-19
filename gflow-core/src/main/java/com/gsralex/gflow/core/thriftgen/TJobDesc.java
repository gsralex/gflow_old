/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.gsralex.gflow.core.thriftgen;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2018-08-19")
public class TJobDesc implements org.apache.thrift.TBase<TJobDesc, TJobDesc._Fields>, java.io.Serializable, Cloneable, Comparable<TJobDesc> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TJobDesc");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField ACTION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("actionId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField JOB_GROUP_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("jobGroupId", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField PARAMETER_FIELD_DESC = new org.apache.thrift.protocol.TField("parameter", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField INDEX_FIELD_DESC = new org.apache.thrift.protocol.TField("index", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TJobDescStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TJobDescTupleSchemeFactory();

  public long id; // required
  public long actionId; // required
  public long jobGroupId; // required
  public String parameter; // required
  public int index; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    ACTION_ID((short)2, "actionId"),
    JOB_GROUP_ID((short)3, "jobGroupId"),
    PARAMETER((short)4, "parameter"),
    INDEX((short)5, "index");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

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
        case 4: // PARAMETER
          return PARAMETER;
        case 5: // INDEX
          return INDEX;
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
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ID_ISSET_ID = 0;
  private static final int __ACTIONID_ISSET_ID = 1;
  private static final int __JOBGROUPID_ISSET_ID = 2;
  private static final int __INDEX_ISSET_ID = 3;
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
    tmpMap.put(_Fields.PARAMETER, new org.apache.thrift.meta_data.FieldMetaData("parameter", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.INDEX, new org.apache.thrift.meta_data.FieldMetaData("index", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TJobDesc.class, metaDataMap);
  }

  public TJobDesc() {
  }

  public TJobDesc(
    long id,
    long actionId,
    long jobGroupId,
    String parameter,
    int index)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.actionId = actionId;
    setActionIdIsSet(true);
    this.jobGroupId = jobGroupId;
    setJobGroupIdIsSet(true);
    this.parameter = parameter;
    this.index = index;
    setIndexIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TJobDesc(TJobDesc other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.actionId = other.actionId;
    this.jobGroupId = other.jobGroupId;
    if (other.isSetParameter()) {
      this.parameter = other.parameter;
    }
    this.index = other.index;
  }

  public TJobDesc deepCopy() {
    return new TJobDesc(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setActionIdIsSet(false);
    this.actionId = 0;
    setJobGroupIdIsSet(false);
    this.jobGroupId = 0;
    this.parameter = null;
    setIndexIsSet(false);
    this.index = 0;
  }

  public long getId() {
    return this.id;
  }

  public TJobDesc setId(long id) {
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

  public TJobDesc setActionId(long actionId) {
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

  public TJobDesc setJobGroupId(long jobGroupId) {
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

  public String getParameter() {
    return this.parameter;
  }

  public TJobDesc setParameter(String parameter) {
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

  public TJobDesc setIndex(int index) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Long)value);
      }
      break;

    case ACTION_ID:
      if (value == null) {
        unsetActionId();
      } else {
        setActionId((Long)value);
      }
      break;

    case JOB_GROUP_ID:
      if (value == null) {
        unsetJobGroupId();
      } else {
        setJobGroupId((Long)value);
      }
      break;

    case PARAMETER:
      if (value == null) {
        unsetParameter();
      } else {
        setParameter((String)value);
      }
      break;

    case INDEX:
      if (value == null) {
        unsetIndex();
      } else {
        setIndex((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case ACTION_ID:
      return getActionId();

    case JOB_GROUP_ID:
      return getJobGroupId();

    case PARAMETER:
      return getParameter();

    case INDEX:
      return getIndex();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case ACTION_ID:
      return isSetActionId();
    case JOB_GROUP_ID:
      return isSetJobGroupId();
    case PARAMETER:
      return isSetParameter();
    case INDEX:
      return isSetIndex();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TJobDesc)
      return this.equals((TJobDesc)that);
    return false;
  }

  public boolean equals(TJobDesc that) {
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

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(id);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(actionId);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(jobGroupId);

    hashCode = hashCode * 8191 + ((isSetParameter()) ? 131071 : 524287);
    if (isSetParameter())
      hashCode = hashCode * 8191 + parameter.hashCode();

    hashCode = hashCode * 8191 + index;

    return hashCode;
  }

  @Override
  public int compareTo(TJobDesc other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetActionId()).compareTo(other.isSetActionId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetActionId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.actionId, other.actionId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetJobGroupId()).compareTo(other.isSetJobGroupId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetJobGroupId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.jobGroupId, other.jobGroupId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetParameter()).compareTo(other.isSetParameter());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParameter()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.parameter, other.parameter);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIndex()).compareTo(other.isSetIndex());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIndex()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.index, other.index);
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
  public String toString() {
    StringBuilder sb = new StringBuilder("TJobDesc(");
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TJobDescStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TJobDescStandardScheme getScheme() {
      return new TJobDescStandardScheme();
    }
  }

  private static class TJobDescStandardScheme extends org.apache.thrift.scheme.StandardScheme<TJobDesc> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TJobDesc struct) throws org.apache.thrift.TException {
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
          case 4: // PARAMETER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.parameter = iprot.readString();
              struct.setParameterIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // INDEX
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.index = iprot.readI32();
              struct.setIndexIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TJobDesc struct) throws org.apache.thrift.TException {
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
      if (struct.parameter != null) {
        oprot.writeFieldBegin(PARAMETER_FIELD_DESC);
        oprot.writeString(struct.parameter);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(INDEX_FIELD_DESC);
      oprot.writeI32(struct.index);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TJobDescTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TJobDescTupleScheme getScheme() {
      return new TJobDescTupleScheme();
    }
  }

  private static class TJobDescTupleScheme extends org.apache.thrift.scheme.TupleScheme<TJobDesc> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TJobDesc struct) throws org.apache.thrift.TException {
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
      if (struct.isSetParameter()) {
        optionals.set(3);
      }
      if (struct.isSetIndex()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetId()) {
        oprot.writeI64(struct.id);
      }
      if (struct.isSetActionId()) {
        oprot.writeI64(struct.actionId);
      }
      if (struct.isSetJobGroupId()) {
        oprot.writeI64(struct.jobGroupId);
      }
      if (struct.isSetParameter()) {
        oprot.writeString(struct.parameter);
      }
      if (struct.isSetIndex()) {
        oprot.writeI32(struct.index);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TJobDesc struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
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
        struct.parameter = iprot.readString();
        struct.setParameterIsSet(true);
      }
      if (incoming.get(4)) {
        struct.index = iprot.readI32();
        struct.setIndexIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

